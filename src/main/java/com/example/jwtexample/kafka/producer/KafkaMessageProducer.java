package com.example.jwtexample.kafka.producer;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaMessageProducer {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  public CompletableFuture<SendResult<String, Object>> send(String topic, String key, Object payload) {
    var message = MessageBuilder
        .withPayload(payload)
        .setHeader(KafkaHeaders.TOPIC, topic)
        .setHeader(KafkaHeaders.KEY, key)
        .build();

    return kafkaTemplate.send(message);
  }

  public CompletableFuture<SendResult<String, Object>> send(String topic, Object payload) {
    return send(topic, null, payload);
  }

  public void logResult(CompletableFuture<SendResult<String, Object>> future) {
    future.whenComplete((result, e) -> {
      if (e == null) {
        log.info("Kafka -> Sent to topic={} offset={}",
            result.getRecordMetadata().topic(),
            result.getRecordMetadata().offset());
      } else {
        log.error("Kafka -> Failed to send message: {}", e.getMessage(), e);
      }
    });
  }
}
