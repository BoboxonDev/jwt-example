package com.example.jwtexample.catalog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogResponseDto {

    private Long id;

    private String title;

    private Long categoryId;

    private Long subcategoryId;

    private String address;

    private String phone;

    private String description;

    private String fileName;

    private String filePath;

    private String fileType;

    private String fileSize;

    private String url;

    private Long companyId;

    public CatalogResponseDto(
            Long id,
            String title,
            Long categoryId,
            Long subcategoryId,
            String address,
            String phone,
            String description,
            String url,
            Long companyId
    ) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.subcategoryId = subcategoryId;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.url = url;
        this.companyId = companyId;
    }
}
