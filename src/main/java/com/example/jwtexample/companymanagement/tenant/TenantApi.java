package com.example.jwtexample.companymanagement.tenant;

import com.example.jwtexample.companymanagement.tenant.dto.TenantRequestDto;
import com.example.jwtexample.companymanagement.tenant.dto.TenantResponseDto;
import com.example.jwtexample.companymanagement.tenant.dto.TenantUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/tenants")
@Tag(name = "Tenants")
public interface TenantApi {

    @GetMapping
    @Operation(summary = "Get Tenants",
            description = "Retrieve a list of all tenants stored in the system")
    ResponseEntity<Page<TenantResponseDto>> getAllTenants(
            TenantFilterParams filterParams,
            Pageable pageable
    );

    @GetMapping("{id}")
    @Operation(summary = "Get tenant by ID",
            description = "Retrieve the details of a specific tenant using its unique ID")
    ResponseEntity<TenantResponseDto> getTenantById(@PathVariable Long id);

    @PostMapping("{id}")
    @Operation(summary = "Create Tenant",
            description = "Create a new tenant with the provided request body and save it to the system")
    ResponseEntity<TenantResponseDto> createTenant(@Valid @RequestBody TenantRequestDto requestDto);

    @PatchMapping("{id}")
    @Operation(summary = "Update Tenant",
            description = "Update an existing tenant partially using the provided ID and request body")
    ResponseEntity<TenantResponseDto> updateTenant(
            @PathVariable Long id,
            @Valid @RequestBody TenantUpdateRequestDto requestDto);

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Tenant",
            description = "Delete an existing tenant from the system using its unique ID")
    ResponseEntity<Void> deleteTenant(@PathVariable Long id);
}
