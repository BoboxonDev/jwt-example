package com.example.jwtexample.category;

import com.example.jwtexample.category.dto.CategoryRequestDto;
import com.example.jwtexample.category.dto.CategoryResponseDto;
import com.example.jwtexample.category.dto.CategoryUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api/v1/categories")
@Tag(name = "Categories")
public interface CategoryApi {

    @GetMapping
    @Operation(summary = "Get all Categories")
    ResponseEntity<Page<CategoryResponseDto>> getAll(
            CategoryFilterParams filterParams, Pageable pageable);

    @GetMapping("{id}")
    @Operation(summary = "Get Category by ID")
    ResponseEntity<CategoryResponseDto> getById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "Create Category")
    ResponseEntity<Void> create(@Valid @RequestBody CategoryRequestDto dto);

    @PatchMapping("{id}")
    @Operation(summary = "Update Category status")
    ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody CategoryUpdateRequestDto dto);

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Category")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
