package com.example.jwtexample.subcategory;

import com.example.jwtexample.subcategory.dto.SubCategoryRequestDto;
import com.example.jwtexample.subcategory.dto.SubCategoryResponseDto;
import com.example.jwtexample.subcategory.dto.SubCategoryUpdateRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SubCategoryController implements SubCategoryApi {

    private final SubCategoryService service;

    @Override
    public ResponseEntity<Page<SubCategoryResponseDto>> getAll(SubCategoryFilterParams filter, Pageable pageable) {
        return ResponseEntity.ok(service.getAll(filter, pageable));
    }

    @Override
    public ResponseEntity<SubCategoryResponseDto> getById(Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<Void> create(@Valid @RequestBody SubCategoryRequestDto dto) {
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> update(Long id, SubCategoryUpdateRequestDto dto) {
        service.update(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
