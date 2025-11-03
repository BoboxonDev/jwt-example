package com.example.jwtexample.company;

import com.example.jwtexample.company.dto.CompanyResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {

  Optional<CompanyEntity> findByIdAndDeletedAtIsNull(Long id);

  @Query("""
          select new com.example.jwtexample.company.dto.CompanyResponseDto(
              entity.id,
              entity.companyInn,
              entity.pinfl,
              entity.companyName,
              entity.companyAddress,
              entity.phoneNumber,
              entity.email,
              entity.vatCode,
              entity.specialAccount,
              entity.directorInn,
              entity.directorPinfl,
              entity.directorName,
              entity.accountant,
              entity.oked,
              entity.taxGap,
              entity.taxPayerTypeName
          )
          from CompanyEntity entity
          where entity.deletedAt is null
            and (:#{#filterParams.companyInn} is null or lower(entity.companyInn) like lower(concat('%', :#{#filterParams.companyInn}, '%')))
            and (:#{#filterParams.companyName} is null or lower(entity.companyName) like lower(concat('%', :#{#filterParams.companyName}, '%')))
            and (:#{#filterParams.directorName} is null or lower(entity.directorName) like lower(concat('%', :#{#filterParams.directorName}, '%')))
            and (:#{#filterParams.vatCode} is null or entity.vatCode = :#{#filterParams.vatCode})
      """)
  Page<CompanyResponseDto> findAllCompanies(CompanyFilterParams filterParams, Pageable pageable);

  boolean existsByPinflAndDeletedAtIsNull(String pinfl);
  boolean existsByCompanyInnAndDeletedAtIsNull(String companyInn);

}
