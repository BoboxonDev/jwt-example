package com.example.jwtexample.companymanagement.tenant;

import com.example.jwtexample.companymanagement.tenant.dto.TenantRequestDto;
import com.example.jwtexample.companymanagement.tenant.dto.TenantResponseDto;
import com.example.jwtexample.companymanagement.tenant.dto.TenantUpdateRequestDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TenantMapper {

    TenantEntity toEntity(TenantRequestDto requestDto);

    TenantResponseDto toDto(TenantEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget TenantEntity entity, TenantUpdateRequestDto requestDto);
}
