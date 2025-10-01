package com.example.jwtexample.kafka;

import com.example.jwtexample.kafka.dto.AdditionReconciliationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageProducer {

    private final KafkaTemplate<String, AdditionReconciliationDto> kafkaTemplate;

    public void sendAdditionMessage(AdditionReconciliationDto dto) {
        kafkaTemplate.send(KafkaConstants.ADDITION_RECONCILIATION_TOPIC, dto);
    }
}
