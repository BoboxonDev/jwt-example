package com.example.jwtexample.subcategory;

import com.example.jwtexample.subcategory.dto.SubCategoryRequestDto;
import com.example.jwtexample.subcategory.dto.SubCategoryResponseDto;
import com.example.jwtexample.subcategory.dto.SubCategoryUpdateRequestDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;


import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SubCategoryMapper {

    SubCategoryEntity toEntity(SubCategoryRequestDto requestDto);

    SubCategoryResponseDto toDto(SubCategoryEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget SubCategoryEntity entity, SubCategoryUpdateRequestDto requestDto);
}