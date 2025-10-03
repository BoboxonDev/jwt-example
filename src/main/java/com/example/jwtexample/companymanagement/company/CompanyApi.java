package com.example.jwtexample.companymanagement.company;

import com.example.jwtexample.companymanagement.company.dto.CompanyRequestDto;
import com.example.jwtexample.companymanagement.company.dto.CompanyResponseDto;
import com.example.jwtexample.companymanagement.company.dto.CompanyUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/companies")
@Tag(name = "Companies")
public interface CompanyApi {

    @GetMapping
    @Operation(summary = "Get all Companies",
            description = "Retrieve a list of all companies stored in the system")
    ResponseEntity<Page<CompanyResponseDto>> getCompany(
            CompanyFilterParams filterParams, Pageable pageable
    );

    @GetMapping("{id}")
    @Operation(summary = "Get Company by ID",
            description = "Retrieve the details of a specific company using its unique ID")
    ResponseEntity<CompanyResponseDto> getById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "Create Company",
            description = "Create a new company with the provided request body and save it to the system")
    ResponseEntity<CompanyResponseDto> createCompany(@Valid @RequestBody CompanyRequestDto requestDto);

    @PatchMapping("{id}")
    @Operation(summary = "Update Company",
            description = "Update an existing company partially using the provided ID and request body")
    ResponseEntity<CompanyResponseDto> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyUpdateRequestDto updateRequestDto
    );

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Company",
            description = "Delete an existing company from the system using its unique ID")
    ResponseEntity<Void> deleteCompany(@PathVariable Long id);
}
