package com.example.jwtexample.common.exception;

public class DuplicateException extends RuntimeException {

    public DuplicateException(String message) {super(message);}

    public DuplicateException(String message, Throwable cause) {super(message, cause);}
}
