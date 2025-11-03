package com.example.jwtexample.catalog;

import com.example.jwtexample.catalog.dto.CatalogResponseDto;
import com.example.jwtexample.catalog.dto.CatalogUpdateRequestDto;
import com.example.jwtexample.fileservice.FileUploadResponseDto;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CatalogMapper {

    @Mapping(target = "fileName", source = "objectName")
    CatalogEntity toEntity(FileUploadResponseDto dto);

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "subcategory.id", target = "subcategoryId")
    @Mapping(source = "company.id", target = "companyId")
    CatalogResponseDto toDto(CatalogEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget CatalogEntity entity, CatalogUpdateRequestDto requestDto);
}
