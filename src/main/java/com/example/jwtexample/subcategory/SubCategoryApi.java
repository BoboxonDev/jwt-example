package com.example.jwtexample.subcategory;

import com.example.jwtexample.subcategory.dto.SubCategoryRequestDto;
import com.example.jwtexample.subcategory.dto.SubCategoryResponseDto;
import com.example.jwtexample.subcategory.dto.SubCategoryUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/v1/sub-categories")
@Tag(name = "SubCategories")
public interface SubCategoryApi {

    @GetMapping
    @Operation(summary = "Get all SubCategories")
    ResponseEntity<Page<SubCategoryResponseDto>> getAll(
            SubCategoryFilterParams filter, Pageable pageable);

    @GetMapping("{id}")
    @Operation(summary = "Get SubCategory by ID")
    ResponseEntity<SubCategoryResponseDto> getById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "Create SubCategory")
    ResponseEntity<Void> create(@Valid @RequestBody SubCategoryRequestDto dto);

    @PatchMapping("{id}")
    @Operation(summary = "Update SubCategory status")
    ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody SubCategoryUpdateRequestDto dto);

    @DeleteMapping("{id}")
    @Operation(summary = "Delete SubCategory")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
