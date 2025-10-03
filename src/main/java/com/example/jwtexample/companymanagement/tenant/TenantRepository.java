package com.example.jwtexample.companymanagement.tenant;

import com.example.jwtexample.companymanagement.tenant.dto.TenantResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity, Long> {

    Optional<TenantEntity> findByIdAndDeletedAtIsNull(Long id);

    @Query("""
       select new com.example.jwtexample.companymanagement.tenant.dto.TenantResponseDto(
       tenant.id,
       tenant.company.id,
       company.name,
       company.email,
       company.phoneNumber,
       company.isActive,
       tenant.name,
       tenant.subscriptionPlan,
       tenant.isActive
       ) from TenantEntity tenant
       left join tenant.company company
       where tenant.deletedAt is null
     and (:#{#filterParams.id} is null or tenant.id = :#{#filterParams.id})
     and (:#{#filterParams.name} is null or lower(tenant.name) like lower(concat('%', :#{#filterParams.name}, '%')))
     and (:#{#filterParams.companyId} is null or tenant.company.id = :#{#filterParams.companyId})
     and (:#{#filterParams.subscriptionPlan} is null or lower(tenant.subscriptionPlan) like lower(concat('%', :#{#filterParams.subscriptionPlan}, '%')))
     and (:#{#filterParams.isActive} is null or tenant.isActive = :#{#filterParams.isActive})
""")
    Page<TenantResponseDto> getAllTenants(TenantFilterParams filterParams, Pageable pageable);
}
