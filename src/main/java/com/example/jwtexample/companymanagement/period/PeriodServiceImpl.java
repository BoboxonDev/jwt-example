package com.example.jwtexample.companymanagement.period;

import com.example.jwtexample.common.exception.ResourceNotFoundException;
import com.example.jwtexample.companymanagement.period.dto.PeriodRequestDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodResponseDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodUpdateRequestDto;
import com.example.jwtexample.companymanagement.tenant.TenantRepository;
import com.example.jwtexample.usermanagment.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PeriodServiceImpl implements PeriodService {

    private final PeriodRepository periodRepository;
    private final PeriodMapper periodMapper;
    private final TenantRepository tenantRepository;

    @Override
    public Page<PeriodResponseDto> getAllPeriods(PeriodFilterParams filterParams, Pageable pageable) {
        return periodRepository.findAllPeriods(filterParams, pageable);
    }

    @Override
    public PeriodResponseDto getById(Long periodId) {
        var period = getPeriod(periodId);

        return periodMapper.toDto(period);
    }

    @Override
    public PeriodEntity getPeriod(Long periodId) {
        var period = periodRepository.findByIdAndDeletedAtIsNull(periodId)
                .orElseThrow(() -> new ResourceNotFoundException("Period not found: " + periodId));

        var tenant = tenantRepository.findByIdAndDeletedAtIsNull(period.getTenant().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Tenant not found" + period.getTenant().getId()));
        period.setTenant(tenant);

        return period;
    }

    @Override
    public PeriodResponseDto createPeriod(PeriodRequestDto requestDto) {
        var period = periodMapper.toEntity(requestDto);

        Optional.ofNullable(requestDto.getTenantId())
                .ifPresent(id -> {
                    var tenant = tenantRepository.findByIdAndDeletedAtIsNull(id)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "The tenant with the ID was not found: " + id
                            ));
                    period.setTenant(tenant);
                });

        return periodMapper.toDto(periodRepository.save(period));
    }

    @Override
    public PeriodResponseDto updatePeriod(Long periodId, PeriodUpdateRequestDto requestDto) {
        var period = getPeriod(periodId);

        Optional.ofNullable(requestDto.getTenantId())
                .ifPresent(id -> {
                    var tenant = tenantRepository.findByIdAndDeletedAtIsNull(id)
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "The tenant with the ID was not found: " + id
                            ));
                    period.setTenant(tenant);
                });

        periodMapper.partialUpdate(period, requestDto);

        return periodMapper.toDto(periodRepository.save(period));
    }

    @Override
    public void deletePeriod(Long id) {
        var period = getPeriod(id);
        period.setDeletedAt(LocalDateTime.now());

        period.setDeletedBy(CurrentUser.getUserId());
        periodRepository.save(period);
    }
}
