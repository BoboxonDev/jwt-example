package com.example.jwtexample.category;

import com.example.jwtexample.category.dto.CategoryRequestDto;
import com.example.jwtexample.category.dto.CategoryResponseDto;
import com.example.jwtexample.category.dto.CategoryUpdateRequestDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CategoryMapper {

    CategoryEntity toEntity(CategoryRequestDto requestDto);

    CategoryResponseDto toDto(CategoryEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget CategoryEntity entity, CategoryUpdateRequestDto requestDto);
}