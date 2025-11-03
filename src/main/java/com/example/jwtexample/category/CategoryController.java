package com.example.jwtexample.category;

import com.example.jwtexample.category.dto.CategoryRequestDto;
import com.example.jwtexample.category.dto.CategoryResponseDto;
import com.example.jwtexample.category.dto.CategoryUpdateRequestDto;
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
public class CategoryController implements CategoryApi {

    private final CategoryService service;

    @Override
    public ResponseEntity<Page<CategoryResponseDto>> getAll(CategoryFilterParams filterParams, Pageable pageable) {
        return ResponseEntity.ok(service.getAll(filterParams, pageable));
    }

    @Override
    public ResponseEntity<CategoryResponseDto> getById(Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @Override
    public ResponseEntity<Void> create(@Valid @RequestBody CategoryRequestDto dto) {
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> update(Long id, CategoryUpdateRequestDto dto) {
        service.update(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
