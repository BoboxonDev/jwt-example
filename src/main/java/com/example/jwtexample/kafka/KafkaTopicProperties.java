package com.example.jwtexample.kafka;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.kafka.topics")
@Data
public class KafkaTopicProperties {

  private String sendAuditLogTopic;
}
