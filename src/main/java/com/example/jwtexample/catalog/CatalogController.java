package com.example.jwtexample.catalog;

import com.example.jwtexample.catalog.dto.CatalogRequestDto;
import com.example.jwtexample.catalog.dto.CatalogResponseDto;
import com.example.jwtexample.catalog.dto.CatalogUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogController implements CatalogApi {

    private final CatalogService service;

    @Override
    public ResponseEntity<Page<CatalogResponseDto>> getAll(CatalogFilterParams filterParams, Pageable pageable) {
        return ResponseEntity.ok(service.getAll(filterParams, pageable));
    }

    @Override
    public ResponseEntity<List<CatalogResponseDto>> listByUserId(Long userId) {
        return ResponseEntity.ok(service.getByUserId(userId));
    }

    @Override
    public ResponseEntity<CatalogResponseDto> uploadFile(CatalogRequestDto dto, MultipartFile file) {
        var created = service.create(dto, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Override
    public ResponseEntity<Void> update(Long id, CatalogUpdateRequestDto dto) {
        service.update(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
