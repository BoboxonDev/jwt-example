package com.example.jwtexample.kafka;

import com.example.jwtexample.kafka.dto.AdditionReconciliationDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

//@ConditionalOnProperty("spring.kafka.consumer.common.enabled")
@Configuration
@RequiredArgsConstructor
public class CommonKafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaServer;

    @Value("${spring.kafka.consumer.common.max-poll-interval-ms}")
//  private Integer maxPollIntervalMs;
//
//  @Value("${spring.kafka.consumer.common.batch-size}")
//  private Integer batchSize;

    public Map<String, Object> commonConsumerConfig() {
        var props = new HashMap<String, Object>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 1200000);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return props;
    }

    @Bean("commonKafkaConsumerFactory")
    public DefaultKafkaConsumerFactory<String, AdditionReconciliationDto> commonConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                org.springframework.kafka.support.serializer.JsonDeserializer.class);
        props.put(org.springframework.kafka.support.serializer.JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean("commonKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, AdditionReconciliationDto> commonKafkaListenerContainerFactory(
            @Qualifier("commonKafkaConsumerFactory") DefaultKafkaConsumerFactory<String, AdditionReconciliationDto> factory) {
        var containerFactory = new ConcurrentKafkaListenerContainerFactory<String, AdditionReconciliationDto>();
        containerFactory.setConsumerFactory(factory);
        containerFactory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return containerFactory;
    }

}
