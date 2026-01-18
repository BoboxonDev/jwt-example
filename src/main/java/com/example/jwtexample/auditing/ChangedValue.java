package com.example.jwtexample.auditing;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangedValue {

  private Object before;
  private Object after;
}
