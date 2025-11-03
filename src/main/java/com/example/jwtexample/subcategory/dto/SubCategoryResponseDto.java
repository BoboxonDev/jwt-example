package com.example.jwtexample.subcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryResponseDto {

    private Long id;

    private String code;

    private String name;
}
