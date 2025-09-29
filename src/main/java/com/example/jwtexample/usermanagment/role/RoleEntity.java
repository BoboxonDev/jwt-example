package com.example.jwtexample.usermanagment.role;

import com.example.jwtexample.common.entity.BaseEntity;
import com.example.jwtexample.enums.ERole;
import com.example.jwtexample.usermanagment.permission.PermissionEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class RoleEntity extends BaseEntity {

    public static final String GENERATOR_NAME = "roles_gen";
    public static final String SEQUENCE_NAME = "roles_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "tenant_id")
    private Long tenantId;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private ERole name;

    @Column(name = "description", nullable = false, length = 250)
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private List<PermissionEntity> permissions;
}
