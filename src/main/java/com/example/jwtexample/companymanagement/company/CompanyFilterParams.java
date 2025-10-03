package com.example.jwtexample.companymanagement.company;

import lombok.Data;

@Data
public class CompanyFilterParams {

    private Long id;

    private String name;

    private String email;

    private String phoneNumber;

    private Boolean isActive;
}
