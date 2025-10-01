package com.example.jwtexample.kafka;

import com.example.jwtexample.kafka.dto.AdditionReconciliationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InternalReconciliationServiceImpl implements InternalReconciliationService{
    @Override
    public void additionReconciliation(AdditionReconciliationDto dto) {
        // TODO: implement your business logic here
        log.info("Processing addition reconciliation: {}", dto);
    }
}
