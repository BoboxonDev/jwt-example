package com.example.jwtexample.usermanagment.action;

import com.example.jwtexample.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "actions")
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class ActionEntity extends BaseEntity {

    public static final String GENERATOR_NAME = "action_gen";
    public static final String SEQUENCE_NAME = "action_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
    @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name; // create, read, update, delete

    @Column(name = "description", length = 255)
    private String description;
}
