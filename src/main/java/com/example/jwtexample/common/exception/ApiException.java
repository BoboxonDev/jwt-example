package com.example.jwtexample.common.exception;

public class ApiException extends RuntimeException{

    private final ApiError apiError;

    public ApiException(ApiError apiError) {
        super(apiError.getMessage());
        this.apiError = apiError;
    }

    public ApiError getApiError() {return apiError;}
}
