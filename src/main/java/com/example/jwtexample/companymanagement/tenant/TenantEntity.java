package com.example.jwtexample.companymanagement.tenant;

import com.example.jwtexample.common.entity.BaseEntity;
import com.example.jwtexample.companymanagement.company.CompanyEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "tenants")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class TenantEntity extends BaseEntity {

    public static final String GENERATOR_NAME = "tenants_gen";
    public static final String SEQUENCE_NAME = "tenants_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "subscription_plan", length = 100)
    private String subscriptionPlan;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;
}
