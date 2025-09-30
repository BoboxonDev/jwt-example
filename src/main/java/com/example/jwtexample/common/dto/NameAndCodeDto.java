package com.example.jwtexample.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NameAndCodeDto {

    private String code;

    private String nameUz;

    private String nameRu;

    private String nameEn;

    public NameAndCodeDto(String code, String nameUz, String nameRu, String nameEn) {
        this.code = code;
        this.nameUz = nameUz;
        this.nameRu = nameRu;
        this.nameEn = nameEn;
    }
}
