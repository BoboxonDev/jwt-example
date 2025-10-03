package com.example.jwtexample.companymanagement.company.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CompanyRequestDto {

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[0-9]{9,15}$", message = "Phone number must be between 9 and 15 digits")
    private String phoneNumber;

    private Boolean isActive;
}

