package com.example.jwtexample.company;

import com.example.jwtexample.company.dto.CompanyRequestDto;
import com.example.jwtexample.company.dto.CompanyResponseDto;
import com.example.jwtexample.company.dto.CompanyUpdateRequestDto;
import org.mapstruct.*;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper {

  CompanyResponseDto toDto(CompanyEntity entity);

  CompanyEntity toEntity(CompanyRequestDto requestDto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void partialUpdate(@MappingTarget CompanyEntity entity, CompanyUpdateRequestDto updateRequestDto);
}