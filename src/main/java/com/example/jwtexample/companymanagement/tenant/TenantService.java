package com.example.jwtexample.companymanagement.tenant;

import com.example.jwtexample.companymanagement.tenant.dto.TenantRequestDto;
import com.example.jwtexample.companymanagement.tenant.dto.TenantResponseDto;
import com.example.jwtexample.companymanagement.tenant.dto.TenantUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantService {

    Page<TenantResponseDto> getAllTenants(TenantFilterParams filterParams, Pageable pageable);

    TenantResponseDto getTenantById(Long tenantId);

    TenantEntity getTenant(Long tenantId);

    TenantResponseDto createTenant(TenantRequestDto requestDto);

    TenantResponseDto updateTenant(Long tenantId, TenantUpdateRequestDto requestDto);

    void deleteTenant(Long tenantId);
}
