package com.example.jwtexample.kafka;

import com.example.jwtexample.kafka.dto.AdditionReconciliationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdditionReconciliationConsumer {

    private final InternalReconciliationService internalReconciliationService;

    @KafkaListener(
            topics = KafkaConstants.ADDITION_RECONCILIATION_TOPIC,
            groupId = KafkaConstants.ADDITION_RECONCILIATION_GROUP_ID,
            containerFactory = KafkaConstants.COMMON_LISTENER_CONTAINER_FACTORY
    )

    public void additionReconciliation(
            AdditionReconciliationDto requestDto,
            Acknowledgment acknowledgment
    ) {
        try {
            log.info("Reading by Kafka for addition reconciliation: {}", requestDto);
            internalReconciliationService.additionReconciliation(requestDto);
        } catch (Exception e) {
            log.error("Error processing addition reconciliation: {}", requestDto, e);
        } finally {
            acknowledgment.acknowledge();
        }
    }
}
