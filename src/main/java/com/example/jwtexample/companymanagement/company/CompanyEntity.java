package com.example.jwtexample.companymanagement.company;

import com.example.jwtexample.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "companies")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CompanyEntity extends BaseEntity {

    public static final String GENERATOR_NAME = "companies_gen";
    public static final String SEQUENCE_NAME = "companies_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "email", unique = true, nullable = false, length = 150)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 20)
    private String phoneNumber;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
