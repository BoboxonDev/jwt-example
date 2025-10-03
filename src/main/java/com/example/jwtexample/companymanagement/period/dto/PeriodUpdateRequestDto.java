package com.example.jwtexample.companymanagement.period.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PeriodUpdateRequestDto {

    private Long tenantId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isActive;
}