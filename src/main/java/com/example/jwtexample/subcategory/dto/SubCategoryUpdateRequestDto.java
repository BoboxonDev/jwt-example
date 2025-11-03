package com.example.jwtexample.subcategory.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SubCategoryUpdateRequestDto {

    @Size(max = 6, message = "Code length should not exceed 6 characters")
    private String code;

    private String name;
}
