package com.example.jwtexample.companymanagement.company.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CompanyUpdateRequestDto {

    private String name;

    private String email;

    private String phoneNumber;

    private Boolean isActive;
}
