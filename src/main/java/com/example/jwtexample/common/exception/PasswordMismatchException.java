package com.example.jwtexample.common.exception;

public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException(String message) {super(message);}

    public PasswordMismatchException(String message, Throwable cause) {super(message, cause);}
}
