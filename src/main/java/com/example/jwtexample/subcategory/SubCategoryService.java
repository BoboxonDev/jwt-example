package com.example.jwtexample.subcategory;

import com.example.jwtexample.subcategory.dto.SubCategoryRequestDto;
import com.example.jwtexample.subcategory.dto.SubCategoryResponseDto;
import com.example.jwtexample.subcategory.dto.SubCategoryUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SubCategoryService {

    Page<SubCategoryResponseDto> getAll(SubCategoryFilterParams filterParams, Pageable pageable);

    SubCategoryResponseDto getById(Long id);

    SubCategoryEntity get(Long id);

    void create(SubCategoryRequestDto requestDto);

    void update(Long id, SubCategoryUpdateRequestDto requestDto);

    void delete(Long id);
}