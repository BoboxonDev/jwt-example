package com.example.jwtexample.catalog;

import com.example.jwtexample.auditing.AuditLogAware;
import com.example.jwtexample.category.CategoryEntity;
import com.example.jwtexample.common.entity.BaseEntity;
import com.example.jwtexample.company.CompanyEntity;
import com.example.jwtexample.subcategory.SubCategoryEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "catalogs",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uc_catalogs_title_non_deleted",
                        columnNames = {"title", "deleted_at"}
                )
        })
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CatalogEntity extends BaseEntity implements AuditLogAware {

    public static final String GENERATOR_NAME = "catalogs_gen";
    public static final String SEQUENCE_NAME = "catalogs_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "title", unique = true, nullable = false, length = 100)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "subcategory_id")
    private SubCategoryEntity subcategory;

    @Column(name = "address")
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "file_name", unique = true, nullable = false)
    private String fileName;

    @Column(name = "file_type", nullable = false)
    private String fileType;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "file_size", nullable = false)
    private String fileSize;

    @Column(name = "url", nullable = false)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;
}
