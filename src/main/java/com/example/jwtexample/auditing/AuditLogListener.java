package com.example.jwtexample.auditing;

import com.example.jwtexample.auditing.dto.AuditLogEvent;
import com.example.jwtexample.auditing.dto.AuditLogInfo;
import com.example.jwtexample.enums.OperationEnum;
import com.example.jwtexample.kafka.KafkaTopicProperties;
import com.example.jwtexample.kafka.dto.AuditAllListRequestDto;
import com.example.jwtexample.kafka.producer.KafkaMessageProducer;
import com.example.jwtexample.usermanagment.security.CurrentUser;
import com.example.jwtexample.usermanagment.user.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.event.spi.PreUpdateEvent;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditLogListener implements PreUpdateEventListener, PostInsertEventListener,
    PostUpdateEventListener {

  private final KafkaMessageProducer kafkaMessageProducer;
  private final KafkaTopicProperties topicProperties;
  private final UserRepository userRepository;

  private static final Set<String> IGNORED_FIELDS = Set.of(
      "createdAt",
      "updatedAt",
      "createdBy",
      "updatedBy"
  );

  @Override
  public boolean onPreUpdate(PreUpdateEvent preUpdateEvent) {
    return false;
  }

  @Override
  public void onPostInsert(PostInsertEvent event) {
    if (event.getEntity() instanceof AuditLogAware aware) {
      AbstractEntityPersister persister = (AbstractEntityPersister) event.getPersister();
      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
        @Override
        public void afterCommit() {
          sendEvent(aware, persister, event.getState(), null, OperationEnum.CREATE);
        }
      });
    }
  }

  @Override
  public void onPostUpdate(PostUpdateEvent event) {
    if (event.getEntity() instanceof AuditLogAware aware) {
      AbstractEntityPersister persister = (AbstractEntityPersister) event.getPersister();

      TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
        @Override
        public void afterCommit() {
          Object[] currentState = event.getState();
          Object[] oldState = event.getOldState();

          sendEvent(aware, persister, currentState, oldState, OperationEnum.UPDATE);
        }
      });
    }
  }

  @Override
  public boolean requiresPostCommitHandling(EntityPersister entityPersister) {
    return false;
  }

  private void sendEvent(AuditLogAware aware, AbstractEntityPersister persister,
      Object[] currentState,
      Object[] previousState, OperationEnum operation) {
    AuditLogInfo info = createLogAuditInfo(aware, persister);
    Map<String, ChangedValue> changedValueMap = findChangedValue(persister, currentState,
        previousState);
    log.info("Changed values found: {}", changedValueMap.size());
    log.info("Operation before check: {}", operation);

    if (operation == OperationEnum.CREATE && changedValueMap.isEmpty()) {
      log.info("No changed values for CREATE — forcing one CREATE audit event");
      changedValueMap.put("created", new ChangedValue(null, "Entity created"));
    }

    if (changedValueMap.isEmpty()) {
      return;
    }

    operation = doOperation(changedValueMap, operation);

    List<AuditLogEvent> events = createAuditLogEvents(info, changedValueMap, operation);

    if (!events.isEmpty()) {
      log.info("Sending {} audit events for table {}", events.size(),
          info.getTableName());
    }

    CompletableFuture<SendResult<String, Object>> sendResultCompletableFuture =
        kafkaMessageProducer.send(topicProperties.getSendAuditLogTopic(),
            new AuditAllListRequestDto(events));
    kafkaMessageProducer.logResult(sendResultCompletableFuture);
  }

  private AuditLogInfo createLogAuditInfo(AuditLogAware aware,
      AbstractEntityPersister persister) {

    return new AuditLogInfo(
        aware.getId(),
        persister.getIdentifierTableMapping().getTableName(),
        CurrentUser.getUserId(),
        CurrentUser.getUsername(),
        CurrentUser.getFullName(userRepository)
    );
  }

  private Map<String, ChangedValue> findChangedValue(AbstractEntityPersister persister,
      Object[] currentState,
      Object[] previousState) {
    if (currentState == null) {
      log.info("Current state is null — cannot determine changed values");
      return Collections.emptyMap();
    }

    String[] propertyNames = persister.getPropertyNames();
    Map<String, ChangedValue> changes = new HashMap<>();

    for (int i = 0; i < propertyNames.length; i++) {
      String propertyName = propertyNames[i];

      if (IGNORED_FIELDS.contains(propertyName)) {
        continue;
      }

      String[] columnNames = persister.getPropertyColumnNames(i);
      if (columnNames == null || columnNames.length == 0) {
        continue;
      }

      Object oldValue = previousState != null ? previousState[i] : null;
      Object newValue = currentState[i];

      boolean valueChanged =
          previousState == null || hasValueChanged(i, currentState, previousState);

      if (valueChanged) {
        String column = columnNames[0];
        changes.put(column, new ChangedValue(oldValue, newValue));
        log.info("Detected change in column '{}': old='{}', new='{}'", column, oldValue, newValue);
      }
    }
    log.info("findChangedValues(): total detected = {}", changes.size());
    return changes;

  }

  private boolean hasValueChanged(int index, Object[] currentState, Object[] previousState) {
    if (previousState == null) {
      return currentState[index] != null;
    }
    return !Objects.equals(previousState[index], currentState[index]);
  }

  private OperationEnum doOperation(Map<String, ChangedValue> changedValueMap,
      OperationEnum operation) {
    if (operation == OperationEnum.UPDATE) {
      var deletedAtChange = changedValueMap.get("deleted_at");
      var deletedByChange = changedValueMap.get("deleted_by");

      boolean isSoftDelete =
          (deletedAtChange != null && deletedAtChange.getAfter() != null)
              || (deletedByChange != null && deletedByChange.getAfter() != null);

      if (isSoftDelete) {
        log.info("Soft delete detected, switching operation from UPDATE to DELETE!");
        return OperationEnum.DELETE;
      }
    }
    return operation;
  }

  private List<AuditLogEvent> createAuditLogEvents(
      AuditLogInfo info,
      Map<String, ChangedValue> changedValueMap,
      OperationEnum operation
  ) {
    List<AuditLogEvent> events = new ArrayList<>();

    changedValueMap.forEach((column, changed) -> {
      info.setOldValueId(null);
      info.setNewValueId(null);

      info.setOldValueText(changed.getBefore() == null
          ? null
          : String.valueOf(changed.getBefore()));

      info.setNewValueText(changed.getAfter() == null
          ? null
          : String.valueOf(changed.getAfter()));

      events.add(createSimpleEvent(info, column, operation));
    });

    return events;
  }

  private AuditLogEvent createSimpleEvent(AuditLogInfo info, String key, OperationEnum operation) {
    return new AuditLogEvent(
        info.getRowId(),
        info.getTableName(),
        key,
        info.getOldValueId(),
        info.getOldValueText(),
        info.getNewValueId(),
        info.getNewValueText(),
        operation,
        LocalDateTime.now(),
        info.getUserId(),
        info.getUserFullName(),
        info.getUserLogin()
    );
  }
}
