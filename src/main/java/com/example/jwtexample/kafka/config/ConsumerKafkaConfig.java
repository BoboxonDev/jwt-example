package com.example.jwtexample.kafka.config;

import com.example.jwtexample.kafka.dto.AuditAllListRequestDto;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.io.SerializationException;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.errors.RecordDeserializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties.AckMode;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ConsumerKafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  private static final int RETRY_ATTEMPTS = 3;
  private static final long RETRY_INTERVAL_MS = 10_000L;

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private Map<String, Object> consumerConfig() {
    Map<String, Object> props = new HashMap<>();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);

    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
    props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 1_200_000);

    props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

    props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

    props.put(JsonDeserializer.VALUE_DEFAULT_TYPE,
        AuditAllListRequestDto.class);

    return props;
  }

  @Bean
  public DefaultKafkaConsumerFactory<String, AuditAllListRequestDto> consumerKafkaFactory() {
    return new DefaultKafkaConsumerFactory<>(consumerConfig());
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, AuditAllListRequestDto> kafkaListenerContainerFactory(
      DefaultKafkaConsumerFactory<String, AuditAllListRequestDto> consumerFactory,
      DefaultErrorHandler kafkaErrorHandler
  ) {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, AuditAllListRequestDto>();
    factory.setConsumerFactory(consumerFactory);
    factory.setConcurrency(1);
    factory.getContainerProperties().setPollTimeout(3000L);
    factory.setCommonErrorHandler(kafkaErrorHandler);
    factory.getContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
    return factory;
  }

  @Bean
  public DefaultErrorHandler errorHandler() {
    var recoverer = new DeadLetterPublishingRecoverer(
        kafkaTemplate,
        (record, e) -> {
          log.error("Error processing record. Sending to DLT: {}", record, e);
          return new TopicPartition("dead-letter-topic", record.partition());
        }
    );

    var backOff = new FixedBackOff(RETRY_INTERVAL_MS, RETRY_ATTEMPTS);
    var errorHandler = new DefaultErrorHandler(recoverer, backOff);

    errorHandler.setRetryListeners((record, ex, deliveryAttempt) ->
        log.warn("Retry attempt {}/{} for record {} failed. Exception: {}",
            deliveryAttempt, RETRY_ATTEMPTS, record, ex.getMessage())
    );

    errorHandler.addNotRetryableExceptions(
        InvalidFormatException.class,
        SerializationException.class,
        RecordDeserializationException.class,
        DeserializationException.class,
        IllegalStateException.class
    );

    errorHandler.setAckAfterHandle(false);
    return errorHandler;
  }
}
