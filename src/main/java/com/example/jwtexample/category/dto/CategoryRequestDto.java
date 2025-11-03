package com.example.jwtexample.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryRequestDto {

    @Size(max = 6, message = "Code length should not exceed 6 characters")
    private String code;

    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Code length should not exceed 50 characters")
    private String name;
}
