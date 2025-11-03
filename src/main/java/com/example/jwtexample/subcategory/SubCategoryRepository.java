package com.example.jwtexample.subcategory;

import com.example.jwtexample.subcategory.dto.SubCategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategoryEntity, Long> {

    Optional<SubCategoryEntity> findByIdAndDeletedAtIsNull(Long id);

    @Query("""
            select new com.example.jwtexample.subcategory.dto.SubCategoryResponseDto(
              entity.id,
              entity.code,
              entity.name
            )
            from SubCategoryEntity entity
            where entity.deletedAt is null
              and (:#{#filterParams.code} is null or entity.code = :#{#filterParams.code})
              and (:#{#filterParams.name} is null or entity.name ilike %:#{#filterParams.name}%)
            """)
    Page<SubCategoryResponseDto> findAllSubCategories(SubCategoryFilterParams filterParams, Pageable pageable);
}
