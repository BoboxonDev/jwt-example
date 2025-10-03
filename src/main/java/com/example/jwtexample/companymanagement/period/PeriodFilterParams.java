package com.example.jwtexample.companymanagement.period;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PeriodFilterParams {

    private Long id;

    private Long tenantId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isActive;
}
