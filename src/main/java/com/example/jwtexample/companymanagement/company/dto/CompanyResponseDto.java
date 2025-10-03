package com.example.jwtexample.companymanagement.company.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyResponseDto {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private Boolean isActive;

    public CompanyResponseDto(
            Long id,
            String name,
            String email,
            String phoneNumber,
            Boolean isActive
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
    }

    public CompanyResponseDto(
            String name,
            String email,
            String phoneNumber,
            Boolean isActive
    ) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
    }
}
