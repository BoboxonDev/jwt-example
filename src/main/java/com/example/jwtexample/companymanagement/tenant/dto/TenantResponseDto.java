package com.example.jwtexample.companymanagement.tenant.dto;

import com.example.jwtexample.company.dto.CompanyResponseDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TenantResponseDto {

    private Long id;

    private Long companyId;


    private String name;

    private String subscriptionPlan;

    private Boolean isActive;

    public TenantResponseDto(
            Long id,
            Long companyId,
            String name,
            String subscriptionPlan,
            Boolean isActive
    ) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.subscriptionPlan = subscriptionPlan;
        this.isActive = isActive;
    }
}
