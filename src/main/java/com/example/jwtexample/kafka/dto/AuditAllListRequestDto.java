package com.example.jwtexample.kafka.dto;

import com.example.jwtexample.auditing.dto.AuditLogEvent;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditAllListRequestDto {

  private List<AuditLogEvent> auditLogEvents;
}
