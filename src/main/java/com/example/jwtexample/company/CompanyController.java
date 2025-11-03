package com.example.jwtexample.company;

import com.example.jwtexample.company.dto.CompanyRequestDto;
import com.example.jwtexample.company.dto.CompanyResponseDto;
import com.example.jwtexample.company.dto.CompanyUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class CompanyController implements CompanyApi {

  private final CompanyService service;

  @Override
  public ResponseEntity<Page<CompanyResponseDto>> getAllCompanies(
      CompanyFilterParams filterParams, Pageable pageable) {
    return ResponseEntity.ok(service.getAllCompanies(filterParams, pageable));
  }

  @Override
  public ResponseEntity<CompanyResponseDto> getCompanyById(Long id) {
    return ResponseEntity.ok(service.getCompanyById(id));
  }

  @Override
  public ResponseEntity<Void> createCompany(@Valid CompanyRequestDto requestDto) {
    service.createCompany(requestDto);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @Override
  public ResponseEntity<Void> updateCompany(Long id,
      @Valid CompanyUpdateRequestDto updateRequestDto) {
    service.updateCompany(id, updateRequestDto);
    return ResponseEntity.ok().build();
  }

  @Override
  public ResponseEntity<Void> deleteCompany(Long id) {
    service.deleteCompany(id);
    return ResponseEntity.ok().build();
  }
}
