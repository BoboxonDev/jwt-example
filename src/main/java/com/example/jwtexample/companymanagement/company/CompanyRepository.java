package com.example.jwtexample.companymanagement.company;

import com.example.jwtexample.companymanagement.company.dto.CompanyResponseDto;
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
   select new com.example.jwtexample.companymanagement.company.dto.CompanyResponseDto(
       company.id,
       company.name,
       company.email,
       company.phoneNumber,
       company.isActive
   )
   from CompanyEntity company
   where company.deletedAt is null
     and (:#{#filterParams.id} is null or company.id = :#{#filterParams.id})
     and (:#{#filterParams.name} is null or lower(company.name) like lower(concat('%', :#{#filterParams.name}, '%')))
     and (:#{#filterParams.email} is null or lower(company.email) like lower(concat('%', :#{#filterParams.email}, '%')))
     and (:#{#filterParams.phoneNumber} is null or lower(company.phoneNumber) like lower(concat('%', :#{#filterParams.phoneNumber}, '%')))
     and (:#{#filterParams.isActive} is null or company.isActive = :#{#filterParams.isActive})
""")
    Page<CompanyResponseDto> findAllCompanies(CompanyFilterParams filterParams, Pageable pageable);

    Boolean existsByEmailAndDeletedAtIsNull(String email);

}
