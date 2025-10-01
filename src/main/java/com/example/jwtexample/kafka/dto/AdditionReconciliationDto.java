package com.example.jwtexample.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdditionReconciliationDto {

    private Long transactionId;
    private Long accountId;
    private Double amount;
    private String description;
}
