package com.example.jwtexample.companymanagement.period;

import com.example.jwtexample.companymanagement.period.dto.PeriodRequestDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodResponseDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PeriodService {

    Page<PeriodResponseDto> getAllPeriods(PeriodFilterParams filterParams, Pageable pageable);

    PeriodResponseDto getById(Long periodId);

    PeriodEntity getPeriod(Long periodId);

    PeriodResponseDto createPeriod(PeriodRequestDto requestDto);

    PeriodResponseDto updatePeriod(Long periodId, PeriodUpdateRequestDto requestDto);

    void deletePeriod(Long id);
}
