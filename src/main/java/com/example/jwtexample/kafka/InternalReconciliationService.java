package com.example.jwtexample.kafka;

import com.example.jwtexample.kafka.dto.AdditionReconciliationDto;

public interface InternalReconciliationService {

    void additionReconciliation(AdditionReconciliationDto dto);
}
