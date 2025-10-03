package com.example.jwtexample.companymanagement.period;

import com.example.jwtexample.companymanagement.period.dto.PeriodRequestDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<PeriodEntity, Long> {

    Optional<PeriodEntity> findByIdAndDeletedAtIsNull(Long id);


    @Query("""
      select new com.example.jwtexample.companymanagement.period.dto.PeriodResponseDto(
      period.id,
      period.tenant.id,
      period.startDate,
      period.endDate,
      period.isActive
      ) from PeriodEntity period
      where period.deletedAt is null
      and (:#{#filterParams.id} is null or period.id = :#{#filterParams.id})
      and (:#{#filterParams.tenantId} is null or period.tenant.id = :#{#filterParams.tenantId})
      and (:#{#filterParams.startDate} is null or period.startDate = :#{#filterParams.startDate})
      and (:#{#filterParams.endDate} is null or period.endDate = :#{#filterParams.endDate})
      and (:#{#filterParams.isActive} is null or period.isActive = :#{#filterParams.isActive})
""")
    Page<PeriodResponseDto> findAllPeriods(PeriodFilterParams filterParams, Pageable pageable);
}
