package com.example.jwtexample.usermanagment.role;

import com.example.jwtexample.common.entity.BaseEntity;
import com.example.jwtexample.enums.ERole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "name", nullable = false)
    private ERole name;
}
