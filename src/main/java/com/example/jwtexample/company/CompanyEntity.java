package com.example.jwtexample.company;

import com.example.jwtexample.auditing.AuditLogAware;
import com.example.jwtexample.common.entity.BaseEntity;
import com.example.jwtexample.company.model.CompanyAccount;
import com.example.jwtexample.company.model.CompanyBranch;
import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "companies",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "unique_company_inn_non_deleted",
            columnNames = {"company_inn", "deleted_at"}
        ),
        @UniqueConstraint(
            name = "unique_pinfl_non_deleted",
            columnNames = {"pinfl", "deleted_at"}
        )
    }
)
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class CompanyEntity extends BaseEntity implements AuditLogAware {

  public static final String GENERATOR_NAME = "companies_gen";
  public static final String SEQUENCE_NAME = "companies_seq";

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR_NAME)
  @SequenceGenerator(name = GENERATOR_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
  private Long id;

  @Column(name = "company_inn", nullable = false, unique = true, length = 9)
  private String companyInn;

  @Column(name = "pinfl", unique = true, length = 14)
  private String pinfl;

  @Column(name = "company_name", nullable = true, length = 100)
  private String companyName;

  @Column(name = "company_address")
  private String companyAddress;

  @Column(name = "phone_number", length = 15)
  private String phoneNumber;

  @Column(name = "email", length = 100)
  private String email;

  @Column(name = "vat_code", length = 20)
  private String vatCode;

  @Column(name = "special_account", length = 50)
  private String specialAccount;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private List<CompanyAccount> accounts;

  @Column(name = "director_inn", length = 9)
  private String directorInn;

  @Column(name = "director_pinfl", length = 14)
  private String directorPinfl;

  @Column(name = "director_name", length = 100)
  private String directorName;

  @Column(name = "accountant", length = 100)
  private String accountant;

  @Column(name = "oked", length = 10)
  private String oked;

  @Column(name = "tax_gap", length = 50)
  private String taxGap;

  @Column(name = "tax_payer_type_name", length = 100)
  private String taxPayerTypeName;

  @Type(JsonType.class)
  @Column(columnDefinition = "jsonb")
  private List<CompanyBranch> branches;
}
