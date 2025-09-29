package com.example.jwtexample.usermanagment.resource;

import jakarta.persistence.Entity;

import com.example.jwtexample.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "resources")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ResourceEntity extends BaseEntity {

    public static final String GENERATOR_NAME = "resource_gen";
    public static final String SEQUENCE_NAME = "resource_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;
}

