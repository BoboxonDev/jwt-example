package com.example.jwtexample.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDto {

  private Long id;
  private String companyInn;
  private String pinfl;
  private String companyName;
  private String companyAddress;
  private String phoneNumber;
  private String email;
  private String vatCode;
  private String specialAccount;
  private String directorInn;
  private String directorPinfl;
  private String directorName;
  private String accountant;
  private String oked;
  private String taxGap;
  private String taxPayerTypeName;
}
