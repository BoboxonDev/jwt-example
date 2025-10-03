package com.example.jwtexample.companymanagement.period;

import com.example.jwtexample.companymanagement.period.dto.PeriodRequestDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodResponseDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodUpdateRequestDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PeriodMapper {

    PeriodEntity toEntity(PeriodRequestDto requestDto);

    PeriodResponseDto toDto(PeriodEntity period);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget PeriodEntity period, PeriodUpdateRequestDto requestDto);
}
