package com.example.jwtexample.companymanagement.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TenantRequestDto {

    @NotNull
    private Long companyId;

    @NotBlank
    private String name;

    @NotBlank
    private String subscriptionPlan;

    private Boolean isActive;
}
