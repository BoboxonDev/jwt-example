package com.example.jwtexample.category;

import com.example.jwtexample.category.dto.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByIdAndDeletedAtIsNull(Long id);

    @Query("""
            select new com.example.jwtexample.category.dto.CategoryResponseDto(
              entity.id,
              entity.code,
              entity.name
            )
            from CategoryEntity entity
            where entity.deletedAt is null
              and (:#{#filterParams.code} is null or entity.code = :#{#filterParams.code})
              and (:#{#filterParams.name} is null or entity.name ilike %:#{#filterParams.name}%)
            """)
    Page<CategoryResponseDto> findAllSubCategories(CategoryFilterParams filterParams, Pageable pageable);
}
