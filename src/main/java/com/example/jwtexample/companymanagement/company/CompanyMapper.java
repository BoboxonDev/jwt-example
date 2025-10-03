package com.example.jwtexample.companymanagement.company;

import com.example.jwtexample.companymanagement.company.dto.CompanyRequestDto;
import com.example.jwtexample.companymanagement.company.dto.CompanyResponseDto;
import com.example.jwtexample.companymanagement.company.dto.CompanyUpdateRequestDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CompanyMapper {

    CompanyEntity toEntity(CompanyRequestDto companyRequestDto);

    CompanyResponseDto toDto(CompanyEntity companyEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget CompanyEntity companyEntity, CompanyUpdateRequestDto updateRequestDto);
}
