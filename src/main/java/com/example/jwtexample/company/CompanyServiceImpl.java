package com.example.jwtexample.company;

import com.example.jwtexample.common.exception.DuplicateException;
import com.example.jwtexample.common.exception.ResourceNotFoundException;
import com.example.jwtexample.company.dto.CompanyRequestDto;
import com.example.jwtexample.company.dto.CompanyResponseDto;
import com.example.jwtexample.company.dto.CompanyUpdateRequestDto;
import com.example.jwtexample.usermanagment.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository repository;
  private final CompanyMapper mapper;

  @Override
  public Page<CompanyResponseDto> getAllCompanies(CompanyFilterParams filterParams,
                                                  Pageable pageable) {
    return repository.findAllCompanies(filterParams, pageable);
  }

  @Override
  public CompanyResponseDto getCompanyById(Long id) {
    var company = getCompany(id);
    return mapper.toDto(company);
  }

  @Override
  public CompanyEntity getCompany(Long id) {
    return repository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(() -> {
          log.error("Company not found with ID={}", id);
          return new ResourceNotFoundException("Company not found with ID: " + id);
        });
  }

  @Override
  public void createCompany(CompanyRequestDto requestDto) {

    String pinfl = requestDto.getPinfl();

    if (repository.existsByPinflAndDeletedAtIsNull(pinfl)) {
      throw new DuplicateException("This PINFL already exists for an active company");
    }
    if (repository.existsByCompanyInnAndDeletedAtIsNull(requestDto.getCompanyInn())){
      throw new DuplicateException(("This CompanyInn already exists for an active company"));
    }
    var entity = mapper.toEntity(requestDto);
    repository.save(entity);
  }

  @Override
  public void updateCompany(Long id, CompanyUpdateRequestDto updateRequestDto) {
    var entity = getCompany(id);
    mapper.partialUpdate(entity, updateRequestDto);
    repository.save(entity);
  }

  @Override
  public void deleteCompany(Long id) {
    var entity = getCompany(id);
    entity.setDeletedAt(LocalDateTime.now());
    entity.setDeletedBy(CurrentUser.getUserId());
    repository.save(entity);
  }
}
