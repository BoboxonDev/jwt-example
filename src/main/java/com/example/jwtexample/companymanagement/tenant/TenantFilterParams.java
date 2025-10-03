package com.example.jwtexample.companymanagement.tenant;

import lombok.Data;

@Data
public class TenantFilterParams {

    private Long id;

    private Long companyId;

    private String name;

    private String subscriptionPlan;

    private Boolean isActive;
}
