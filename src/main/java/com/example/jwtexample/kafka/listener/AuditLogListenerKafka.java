package com.example.jwtexample.kafka.listener;

import com.example.jwtexample.kafka.dto.AuditAllListRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(
    topics = "${app.kafka.topics.send-audit-log-topic}",
    groupId = "jwtexample.audit.group",
    containerFactory = "kafkaListenerContainerFactory"
)
@RequiredArgsConstructor
public class AuditLogListenerKafka {

  @KafkaHandler
  public void listen(@Payload AuditAllListRequestDto dto,
      Acknowledgment acknowledgment) {

    try {
      if (dto == null || dto.getAuditLogEvents() == null) {
        log.warn("Received empty AuditAllListRequestDto");
      } else {
        log.info("Received {} audit events from Kafka", dto.getAuditLogEvents().size());

        dto.getAuditLogEvents().forEach(event ->
          log.info("AUDIT EVENT: table={} rowId={} field={} old='{}' new='{}' operation={}",
              event.getTableName(),
              event.getRowId(),
              event.getFieldKey(),
              event.getOldValueText(),
              event.getNewValueText(),
              event.getOperation()
          )
        );
      }
      acknowledgment.acknowledge();
    } catch (Exception e) {
      log.error("Error while processing audit events: {}", dto, e);
    }
  }

  @KafkaHandler(isDefault = true)
  public void listenUnknown(Object unknown) {
    log.error("Unknown message type received in AuditLogListenerKafka: {}", unknown);
  }
}
