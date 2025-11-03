package com.example.jwtexample.company;

import com.example.jwtexample.company.dto.CompanyRequestDto;
import com.example.jwtexample.company.dto.CompanyResponseDto;
import com.example.jwtexample.company.dto.CompanyUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CompanyService {

  Page<CompanyResponseDto> getAllCompanies(CompanyFilterParams filterParams, Pageable pageable);

  CompanyResponseDto getCompanyById(Long id);

  CompanyEntity getCompany(Long id);

  void createCompany(CompanyRequestDto requestDto);

  void updateCompany(Long id, CompanyUpdateRequestDto updateRequestDto);

  void deleteCompany(Long id);

}
