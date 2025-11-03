package com.example.jwtexample.common.exception;

import org.springframework.http.HttpStatus;

public class FileStorageException extends RuntimeException{

    private final HttpStatus status;

    public FileStorageException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {return status;}
}
