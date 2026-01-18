package com.example.jwtexample.lock.account;

import com.example.jwtexample.auditing.AuditLogAware;
import com.example.jwtexample.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
public class AccountEntity extends BaseEntity implements AuditLogAware {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_name")
    private String ownerName;

    @Column(name = "quantity")
    private Double balance;

}
