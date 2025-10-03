package com.example.jwtexample.companymanagement.tenant;

import com.example.jwtexample.common.exception.ResourceNotFoundException;
import com.example.jwtexample.companymanagement.company.CompanyRepository;
import com.example.jwtexample.companymanagement.tenant.dto.TenantRequestDto;
import com.example.jwtexample.companymanagement.tenant.dto.TenantResponseDto;
import com.example.jwtexample.companymanagement.tenant.dto.TenantUpdateRequestDto;
import com.example.jwtexample.usermanagment.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TenantServiceImpl implements TenantService {

    private final TenantRepository tenantRepository;
    private final TenantMapper tenantMapper;
    private final CompanyRepository companyRepository;

    @Override
    public Page<TenantResponseDto> getAllTenants(TenantFilterParams filterParams, Pageable pageable) {
        return tenantRepository.getAllTenants(filterParams, pageable);
    }

    @Override
    public TenantResponseDto getTenantById(Long tenantId) {
        var tenant = getTenant(tenantId);

        return tenantMapper.toDto(tenant);
    }

    @Override
    public TenantEntity getTenant(Long tenantId) {
        var tenant = tenantRepository.findByIdAndDeletedAtIsNull(tenantId)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found: " + tenantId));

        var company = companyRepository.findByIdAndDeletedAtIsNull(tenant.getCompany().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + tenant.getCompany().getId()));
        tenant.setCompany(company);

        return tenant;
    }

    @Override
    public TenantResponseDto createTenant(TenantRequestDto requestDto) {
        var tenant = tenantMapper.toEntity(requestDto);

        Optional.ofNullable(requestDto.getCompanyId())
                .ifPresent(id -> {
                    var company = companyRepository.findByIdAndDeletedAtIsNull(id)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "The company with the ID was not found: " + id));
                    tenant.setCompany(company);
                });

        return tenantMapper.toDto(tenantRepository.save(tenant));
    }

    @Override
    public TenantResponseDto updateTenant(Long tenantId, TenantUpdateRequestDto requestDto) {
        var tenant = getTenant(tenantId);
        tenantMapper.partialUpdate(tenant, requestDto);

        Optional.ofNullable(requestDto.getCompanyId())
                .ifPresent(id -> {
                    var company = companyRepository.findByIdAndDeletedAtIsNull(id)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "The company with the ID was not found: " + id));
                    tenant.setCompany(company);
                });

        return tenantMapper.toDto(tenantRepository.save(tenant));
    }

    @Override
    public void deleteTenant(Long tenantId) {
        var tenant = getTenant(tenantId);
        tenant.setDeletedAt(LocalDateTime.now());

        tenant.setDeletedBy(CurrentUser.getUserId());
        tenantRepository.save(tenant);
    }
}
