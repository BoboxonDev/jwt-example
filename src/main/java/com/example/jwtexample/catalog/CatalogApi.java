package com.example.jwtexample.catalog;

import com.example.jwtexample.catalog.dto.CatalogRequestDto;
import com.example.jwtexample.catalog.dto.CatalogResponseDto;
import com.example.jwtexample.catalog.dto.CatalogUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1/catalogs")
@Tag(name = "Catalog")
public interface CatalogApi {

    @GetMapping
    @Operation(summary = "Get all Catalogs")
    ResponseEntity<Page<CatalogResponseDto>> getAll(
            CatalogFilterParams filterParams, Pageable pageable);

    @GetMapping("{userId}")
    @Operation(summary = "Get Catalogs by user ID")
    ResponseEntity<List<CatalogResponseDto>> listByUserId(@PathVariable Long userId);

    @PostMapping(
            value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @Operation(summary = "Create catalog with file upload")
    ResponseEntity<CatalogResponseDto> uploadFile(
            @RequestPart("dto") CatalogRequestDto dto,
            @RequestPart("file") MultipartFile file);

    @PatchMapping("{id}")
    @Operation(summary = "Update Catalog info")
    ResponseEntity<Void> update(
            @PathVariable Long id,
            @RequestBody CatalogUpdateRequestDto dto);

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Catalog")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
