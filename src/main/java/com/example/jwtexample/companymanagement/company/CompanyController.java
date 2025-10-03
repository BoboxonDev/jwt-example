package com.example.jwtexample.companymanagement.company;

import com.example.jwtexample.companymanagement.company.dto.CompanyRequestDto;
import com.example.jwtexample.companymanagement.company.dto.CompanyResponseDto;
import com.example.jwtexample.companymanagement.company.dto.CompanyUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyController implements CompanyApi{

    private final CompanyService companyService;

    @Override
    public ResponseEntity<Page<CompanyResponseDto>> getCompany(CompanyFilterParams filterParams, Pageable pageable) {
        return ResponseEntity.ok(companyService.getAllCompanies(filterParams, pageable));
    }

    @Override
    public ResponseEntity<CompanyResponseDto> getById(Long id) {
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    @Override
    public ResponseEntity<CompanyResponseDto> createCompany(CompanyRequestDto requestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body( companyService.createCompany(requestDto));
    }

    @Override
    public ResponseEntity<CompanyResponseDto> updateCompany(Long id, CompanyUpdateRequestDto updateRequestDto) {
        return ResponseEntity.ok(companyService.updateCompany(id, updateRequestDto));
    }

    @Override
    public ResponseEntity<Void> deleteCompany(Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.noContent().build();
    }
}
