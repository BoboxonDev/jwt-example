package com.example.jwtexample.companymanagement.period;

import com.example.jwtexample.companymanagement.period.dto.PeriodRequestDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodResponseDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PeriodController implements PeriodApi{

    private final PeriodService periodService;

    @Override
    public ResponseEntity<Page<PeriodResponseDto>> getAllPeriods(
            PeriodFilterParams filterParams,
            Pageable pageable) {
        return ResponseEntity.ok(periodService.getAllPeriods(filterParams, pageable));
    }

    @Override
    public ResponseEntity<PeriodResponseDto> getPeriodById(Long id) {
        return ResponseEntity.ok(periodService.getById(id));
    }

    @Override
    public ResponseEntity<PeriodResponseDto> create(PeriodRequestDto periodRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(periodService.createPeriod(periodRequestDto));
    }

    @Override
    public ResponseEntity<PeriodResponseDto> update(
            Long periodId,
            PeriodUpdateRequestDto periodRequestDto) {
        return ResponseEntity.ok(periodService.updatePeriod(periodId, periodRequestDto));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        periodService.deletePeriod(id);
        return ResponseEntity.ok().build();
    }
}
