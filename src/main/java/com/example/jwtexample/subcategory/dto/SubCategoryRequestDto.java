package com.example.jwtexample.subcategory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubCategoryRequestDto {

    @Size(max = 6, message = "Code length should not exceed 6 characters")
    private String code;

    @NotBlank(message = "Name is required")
    private String name;
}
