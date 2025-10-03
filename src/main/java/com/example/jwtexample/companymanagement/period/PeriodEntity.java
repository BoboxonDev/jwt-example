package com.example.jwtexample.companymanagement.period;

import com.example.jwtexample.common.entity.BaseEntity;
import com.example.jwtexample.companymanagement.tenant.TenantEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@Entity
@Table(name = "periods")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class PeriodEntity extends BaseEntity {

    public static final String GENERATOR_NAME = "periods_gen";
    public static final String SEQUENCE_NAME = "periods_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private TenantEntity tenant;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
}
