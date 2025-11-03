package com.example.jwtexample.company.dto;

import lombok.Data;

@Data
public class CompanyUpdateRequestDto {

  private String companyAddress;

  private String phoneNumber;

  private String email;

  private String vatCode;

  private String specialAccount;
}
