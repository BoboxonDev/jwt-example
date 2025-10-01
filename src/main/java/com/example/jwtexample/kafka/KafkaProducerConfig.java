package com.example.jwtexample.kafka;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig<T> {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean("paymentProducerFactory")
    public ProducerFactory<String, T> paymentProducerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 50000000);
        config.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 67108864);
        return new DefaultKafkaProducerFactory<>(config);
    }

    @Bean("paymentKafkaTemplate")
    public KafkaTemplate<String, T> paymentKafkaTemplate(
            @Qualifier("paymentProducerFactory") ProducerFactory<String, T> paymentProducerFactory
    ) {
        return new KafkaTemplate<>(paymentProducerFactory);
    }
}
