package com.example.jwtexample.company;

import com.example.jwtexample.company.dto.CompanyRequestDto;
import com.example.jwtexample.company.dto.CompanyResponseDto;
import com.example.jwtexample.company.dto.CompanyUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/companies")
@Tag(name = "Companies", description = "Operations for managing company information")
public interface CompanyApi {
  /**
   * Get a paginated list of companies with optional filtering.
   *
   * @param filterParams filter parameters to narrow down the results
   * @param pageable     pagination information (page number, size, sort)
   * @return a paginated list of {@link CompanyResponseDto} wrapped in {@link ResponseEntity}
   */
  @GetMapping
  @Operation(summary = "Get all companies")
  ResponseEntity<Page<CompanyResponseDto>> getAllCompanies(
      CompanyFilterParams filterParams, Pageable pageable);

  /**
   * Get a single company by its unique ID.
   *
   * @param id the unique identifier of the company
   * @return the {@link CompanyResponseDto} of the requested company wrapped in {@link ResponseEntity}
   */
  @GetMapping("/{id}")
  @Operation(summary = "Get a company by ID")
  ResponseEntity<CompanyResponseDto> getCompanyById(@PathVariable Long id);

  /**
   * Create a new company.
   *
   * @param requestDto the {@link CompanyRequestDto} containing company details
   * @return an empty {@link ResponseEntity} with HTTP status 201 (Created)
   */
  @PostMapping
  @Operation(summary = "Create a new company")
  ResponseEntity<Void> createCompany(@Valid @RequestBody CompanyRequestDto requestDto);

  /**
   * Update an existing company by its unique ID.
   *
   * @param id               the unique identifier of the company to update
   * @param updateRequestDto the {@link CompanyUpdateRequestDto} containing updated company details
   * @return an empty {@link ResponseEntity} with HTTP status 200 (OK)
   */
  @PatchMapping("/{id}")
  @Operation(summary = "Update an existing company")
  ResponseEntity<Void> updateCompany(
      @PathVariable Long id,
      @Valid @RequestBody CompanyUpdateRequestDto updateRequestDto);

  /**
   * Delete a company by its unique ID (soft delete if implemented).
   *
   * @param id the unique identifier of the company to delete
   * @return an empty {@link ResponseEntity} with HTTP status 204 (No Content)
   */
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete a company by ID")
  ResponseEntity<Void> deleteCompany(@PathVariable Long id);
}
