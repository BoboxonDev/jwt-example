package com.example.jwtexample.category;

import com.example.jwtexample.category.dto.CategoryRequestDto;
import com.example.jwtexample.category.dto.CategoryResponseDto;
import com.example.jwtexample.category.dto.CategoryUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;



public interface CategoryService {

    Page<CategoryResponseDto> getAll(CategoryFilterParams filterParams, Pageable pageable);

    CategoryResponseDto getById(Long id);

    CategoryEntity get(Long id);

    void create(CategoryRequestDto requestDto);

    void update(Long id, CategoryUpdateRequestDto requestDto);

    void delete(Long id);
}