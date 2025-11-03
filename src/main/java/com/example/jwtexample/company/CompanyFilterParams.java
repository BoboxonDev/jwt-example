package com.example.jwtexample.company;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyFilterParams {

  private String companyInn;

  private String companyName;

  private String directorName;

  private String vatCode;
}
