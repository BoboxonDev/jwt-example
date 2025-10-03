package com.example.jwtexample.companymanagement.period.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PeriodRequestDto {

    @NotNull
    private Long tenantId;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    private Boolean isActive;
}