package com.example.jwtexample.catalog;

import com.example.jwtexample.catalog.dto.CatalogResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {

    Optional<CatalogEntity> findByIdAndDeletedAtIsNull(Long id);

    @Query("""
            select new com.example.jwtexample.catalog.dto.CatalogResponseDto(
                entity.id,
                entity.title,
                category.id,
                subcategory.id,
                entity.address,
                entity.phone,
                entity.description,
                entity.url,
                company.id
            )
            from CatalogEntity entity
            left join entity.category category
            left join entity.subcategory subcategory
            left join entity.company company
            where entity.deletedAt is null
              and (:#{#filterParams.categoryId} is null or entity.category.id = :#{#filterParams.categoryId})
              and (:#{#filterParams.subcategoryId} is null or entity.subcategory.id = :#{#filterParams.subcategoryId})
              and (:#{#filterParams.title} is null or entity.title ilike %:#{#filterParams.title}%)
              and (:#{#filterParams.phone} is null or entity.phone ilike %:#{#filterParams.phone}%)
              and (:#{#filterParams.address} is null or entity.address ilike %:#{#filterParams.address}%)
            """)
    Page<CatalogResponseDto> findAllCatalogs(CatalogFilterParams filterParams, Pageable pageable);

    boolean existsByTitleAndDeletedAtIsNull(String title);

    @Query("""
           select entity from CatalogEntity entity
           where entity.deletedAt is null
           and entity.fileName = :#{#fileName}
           and entity.createdBy = :#{#userId}
""" )
    Optional<CatalogEntity> findByFileNameAndUserIdDeletedAt(String fileName, Long userId);

    @Query("""
           select entity from CatalogEntity entity
           where entity.deletedAt is null and entity.createdBy = :#{#userId}
""")
    Optional<CatalogEntity> findByUserIdAndDeletedAtIsNull(Long userId);
}
