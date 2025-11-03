package com.example.jwtexample.company.dto;

import lombok.Data;

@Data
public class CompanyAccountRequestDto {

  private String bankName;

  private String bankMfo;

  private String accountCode;

  private boolean isPrimary;
}
