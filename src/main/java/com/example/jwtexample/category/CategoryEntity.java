package com.example.jwtexample.category;

import com.example.jwtexample.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "categories",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uc_categories_code_non_deleted",
                        columnNames = {"code", "deleted_at"}
                )
        })
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CategoryEntity extends BaseEntity {

    public static final String GENERATOR_NAME = "categories_gen";
    public static final String SEQUENCE_NAME = "categories_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "code", length = 6)
    private String code;

    @Column(name = "name", length = 50, nullable = false)
    private String name;
}
