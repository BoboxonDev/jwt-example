package com.example.jwtexample.category;

import com.example.jwtexample.category.dto.CategoryRequestDto;
import com.example.jwtexample.category.dto.CategoryResponseDto;
import com.example.jwtexample.category.dto.CategoryUpdateRequestDto;
import com.example.jwtexample.common.exception.ResourceNotFoundException;
import com.example.jwtexample.usermanagment.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public Page<CategoryResponseDto> getAll(CategoryFilterParams filterParams, Pageable pageable) {
        return repository.findAllSubCategories(filterParams, pageable);
    }

    @Override
    public CategoryResponseDto getById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        return mapper.toDto(entity);
    }

    @Override
    public CategoryEntity get(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> {
                    log.error("Category not found with provided ID={}", id);
                    return new ResourceNotFoundException("Category not found with ID: " + id);
                });
    }

    @Override
    public void create(CategoryRequestDto requestDto) {
        var entity = mapper.toEntity(requestDto);
        repository.save(entity);
    }

    @Override
    public void update(Long id, CategoryUpdateRequestDto requestDto) {
        var entity = get(id);
        mapper.partialUpdate(entity, requestDto);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        var entity = get(id);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(CurrentUser.getUserId());
        repository.save(entity);
    }
}
