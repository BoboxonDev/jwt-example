package com.example.jwtexample.company.model;

import lombok.Data;

@Data
public class CompanyAccount {

  private String bankName;

  private String bankMfo;

  private String accountCode;

  private boolean isPrimary;
}
