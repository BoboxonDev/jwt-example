package com.example.jwtexample.companymanagement.company;

import com.example.jwtexample.companymanagement.company.dto.CompanyRequestDto;
import com.example.jwtexample.companymanagement.company.dto.CompanyResponseDto;
import com.example.jwtexample.companymanagement.company.dto.CompanyUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService {

    Page<CompanyResponseDto> getAllCompanies(CompanyFilterParams filterParams, Pageable pageable);

    CompanyResponseDto getCompanyById(Long companyId);

    CompanyEntity getCompany(Long companyId);

    CompanyResponseDto createCompany(CompanyRequestDto requestDto);

    CompanyResponseDto updateCompany(Long id, CompanyUpdateRequestDto requestDto);

    void deleteCompany(Long companyId);
}
