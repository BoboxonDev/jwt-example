package com.example.jwtexample.auditing.dto;

import com.example.jwtexample.enums.OperationEnum;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuditLogEvent {

  private Long rowId;
  private String tableName;
  private String fieldKey;
  private Long oldValueId;
  private String oldValueText;
  private Long newValueId;
  private String newValueText;
  private OperationEnum operation;
  private LocalDateTime updatedAt;
  private Long userId;
  private String userFullName;
  private String userLogin;

  public AuditLogEvent(
      Long rowId,
      String tableName,
      String fieldKey,
      Long oldValueId,
      String oldValueText,
      Long newValueId,
      String newValueText,
      OperationEnum operation,
      LocalDateTime updatedAt,
      Long userId,
      String userFullName,
      String userLogin
  ) {
    this.rowId = rowId;
    this.tableName = tableName;
    this.fieldKey = fieldKey;
    this.oldValueId = oldValueId;
    this.oldValueText = oldValueText;
    this.newValueId = newValueId;
    this.newValueText = newValueText;
    this.operation = operation;
    this.updatedAt = updatedAt;
    this.userId = userId;
    this.userFullName = userFullName;
    this.userLogin = userLogin;
  }
}
