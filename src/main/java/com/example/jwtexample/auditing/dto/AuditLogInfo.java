package com.example.jwtexample.auditing.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogInfo {

  private Long rowId;
  private String tableName;
  private String fieldKey;
  private Long oldValueId;
  private String oldValueText;
  private Long newValueId;
  private String newValueText;
  private Long userId;
  private String userFullName;
  private String userLogin;

  public AuditLogInfo(
      Long rowId,
      String tableName,
      Long userId,
      String userFullName,
      String userLogin
  ) {
    this.rowId = rowId;
    this.tableName = tableName;
    this.userId = userId;
    this.userFullName = userFullName;
    this.userLogin = userLogin;
  }
}
