package com.example.jwtexample.companymanagement.period.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class PeriodResponseDto {

    private Long id;

    private Long tenantId;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean isActive;

    public PeriodResponseDto(
            Long id,
            Long tenantId,
            LocalDate startDate,
            LocalDate endDate,
            Boolean isActive
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }
}