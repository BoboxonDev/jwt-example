package com.example.jwtexample.usermanagment.permission;

import com.example.jwtexample.usermanagment.action.ActionEntity;
import com.example.jwtexample.common.entity.BaseEntity;
import com.example.jwtexample.usermanagment.resource.ResourceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class PermissionEntity extends BaseEntity {

    public static final String GENERATOR_NAME = "permission_gen";
    public static final String SEQUENCE_NAME = "permission_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resource_id", nullable = false)
    private ResourceEntity resource;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "action_id", nullable = false)
    private ActionEntity action;

    @Column(name = "code", nullable = false, unique = true, length = 150)
    private String code;
}
