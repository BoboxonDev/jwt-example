package com.example.jwtexample.common.exception;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorResponse {
    private int status;
    private String message;
    private String source;
    private String errorCode;
    private LocalDateTime timestamp;
}
