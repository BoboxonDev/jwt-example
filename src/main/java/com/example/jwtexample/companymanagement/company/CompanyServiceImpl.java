package com.example.jwtexample.companymanagement.company;

import com.example.jwtexample.common.exception.ResourceNotFoundException;
import com.example.jwtexample.companymanagement.company.dto.CompanyRequestDto;
import com.example.jwtexample.companymanagement.company.dto.CompanyResponseDto;
import com.example.jwtexample.companymanagement.company.dto.CompanyUpdateRequestDto;
import com.example.jwtexample.usermanagment.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public Page<CompanyResponseDto> getAllCompanies(CompanyFilterParams filterParams, Pageable pageable) {
        return companyRepository.findAllCompanies(filterParams, pageable);
    }

    @Override
    public CompanyResponseDto getCompanyById(Long companyId) {
        var company = getCompany(companyId);

        return companyMapper.toDto(company);
    }

    @Override
    public CompanyEntity getCompany(Long companyId) {
        return companyRepository.findByIdAndDeletedAtIsNull(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    @Override
    public CompanyResponseDto createCompany(CompanyRequestDto requestDto) {
        if (requestDto.getEmail() != null &&
                companyRepository.existsByEmailAndDeletedAtIsNull(
                        requestDto.getEmail())) {
            throw new IllegalArgumentException("Company email already exists: " + requestDto.getEmail());
        }
        var company = companyMapper.toEntity(requestDto);
        return companyMapper.toDto(companyRepository.save(company));
    }

    @Override
    public CompanyResponseDto updateCompany(Long id, CompanyUpdateRequestDto updateRequestDto) {
        var company = getCompany(id);
        if (updateRequestDto.getEmail() != null &&
                companyRepository.existsByEmailAndDeletedAtIsNull(
                        updateRequestDto.getEmail())) {
            throw new IllegalArgumentException("Company email already exists: " + updateRequestDto.getEmail());
        }

        companyMapper.update(company, updateRequestDto);
        return companyMapper.toDto(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long companyId) {
        var company = getCompany(companyId);
        company.setDeletedAt(LocalDateTime.now());

        company.setDeletedBy(CurrentUser.getUserId());
        companyRepository.save(company);
    }
}
