package com.example.jwtexample.catalog.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CatalogUpdateRequestDto {

    @Size(max = 100, message = "Title length must not exceed 100 characters")
    private String title;

    private Long categoryId;

    private Long subcategoryId;

    private String address;

    @Size(max = 20, message = "Phone length must not exceed 20 characters")
    @Pattern(regexp = "^(\\+998|998)?\\d{9}$")
    private String phone;

    @Size(max = 500, message = "Description length must not exceed 500 characters")
    private String description;

    private String url;

    private Long companyId;
}
