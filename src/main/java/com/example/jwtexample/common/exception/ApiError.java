package com.example.jwtexample.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private int status;
    private String message;
    private String source;

    public ApiError(HttpStatus status, Throwable e, String source) {
        this.status = status.value();
        this.message = e.getMessage();
        this.source = source;
    }

    public ApiError(HttpStatus status, String message, String source) {
        this.status = status.value();
        this.message = message;
        this.source = source;
    }
}
