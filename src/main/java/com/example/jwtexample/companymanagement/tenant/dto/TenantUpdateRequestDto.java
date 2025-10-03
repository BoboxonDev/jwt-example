package com.example.jwtexample.companymanagement.tenant.dto;

import lombok.Data;

@Data
public class TenantUpdateRequestDto {

    private Long companyId;

    private String name;

    private String subscriptionPlan;

    private Boolean isActive;
}
