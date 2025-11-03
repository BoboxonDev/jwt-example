package com.example.jwtexample.company.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class CompanyRequestDto {

  @NotBlank(message = "Company INN is required")
  @Size(max = 9, message ="Company inn must be at most 9 characters" )
  private String companyInn;

  @Size(max = 14, message = "PINFL must be at most 14 characters")
  @NotBlank(message = "PINFL is required")
  private String pinfl;

  @NotBlank(message = "Company name is required")
  @Size(max = 100, message = "Company name must be at most 100 characters")
  private String companyName;

  private String companyAddress;

  @NotBlank
  @Size(max = 15, message = "Phone number must be at most 15 characters")
  private String phoneNumber;

  @Size(max = 100, message = "Email must be at most 100 characters")
  private String email;

  @Size(max = 20, message = "VAT code must be at most 20 characters")
  private String vatCode;

  @Size(max = 50, message = "Special account must be at most 50 characters")
  private String specialAccount;

  @Size(max = 9, message = "Director INN must be at most 9 characters")
  private String directorInn;

  @Size(max = 14, message = "Director PINFL must be at most 14 characters")
  private String directorPinfl;

  @Size(max = 100, message = "Director name must be at most 100 characters")
  private String directorName;

  @Size(max = 100, message = "Accountant name must be at most 100 characters")
  private String accountant;

  @Size(max = 10, message = "OKED must be at most 10 characters")
  private String oked;

  @Size(max = 50, message = "Tax gap must be at most 50 characters")
  private String taxGap;

  @Size(max = 100, message = "Tax payer type name must be at most 100 characters")
  private String taxPayerTypeName;

  @Valid
  @JsonProperty("Branches")
  private List<CompanyBranchRequestDto> branches;

  @Valid
  private List<CompanyAccountRequestDto> accounts;

}

