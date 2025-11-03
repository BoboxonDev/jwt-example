package com.example.jwtexample.catalog;

import lombok.Data;

@Data
public class CatalogFilterParams {

    private Long categoryId;

    private Long subcategoryId;

    private Long companyId;

    private String title;

    private String phone;

    private String address;
}
