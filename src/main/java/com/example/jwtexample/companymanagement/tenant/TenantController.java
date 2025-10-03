package com.example.jwtexample.companymanagement.tenant;

import com.example.jwtexample.companymanagement.tenant.dto.TenantRequestDto;
import com.example.jwtexample.companymanagement.tenant.dto.TenantResponseDto;
import com.example.jwtexample.companymanagement.tenant.dto.TenantUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TenantController implements TenantApi {

    private final TenantService tenantService;

    @Override
    public ResponseEntity<Page<TenantResponseDto>> getAllTenants(TenantFilterParams filterParams, Pageable pageable) {
        return ResponseEntity.ok(tenantService.getAllTenants(filterParams, pageable));
    }

    @Override
    public ResponseEntity<TenantResponseDto> getTenantById(Long id) {
        return ResponseEntity.ok(tenantService.getTenantById(id));
    }

    @Override
    public ResponseEntity<TenantResponseDto> createTenant(TenantRequestDto requestDto) {
        tenantService.createTenant(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<TenantResponseDto> updateTenant(Long id, TenantUpdateRequestDto requestDto) {
        tenantService.updateTenant(id, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteTenant(Long id) {
        tenantService.deleteTenant(id);
        return ResponseEntity.ok().build();
    }
}
