package com.example.jwtexample.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", HttpStatus.NOT_FOUND),
    BAD_REQUEST("BAD_REQUEST", HttpStatus.BAD_REQUEST),
    DUPLICATE("DUPLICATE_EXCEPTION", HttpStatus.CONFLICT),
    PASSWORD_MISMATCH("PASSWORD_MISMATCH_EXCEPTION", HttpStatus.NOT_ACCEPTABLE);

    private final String code;
    private final HttpStatus status;
}
