package com.example.jwtexample.catalog;

import com.example.jwtexample.catalog.dto.CatalogRequestDto;
import com.example.jwtexample.catalog.dto.CatalogResponseDto;
import com.example.jwtexample.catalog.dto.CatalogUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface CatalogService {

    Page<CatalogResponseDto> getAll(CatalogFilterParams filterParams, Pageable pageable);

    List<CatalogResponseDto> getByUserId(Long userId);

    CatalogEntity get(Long id);

    CatalogResponseDto create(CatalogRequestDto requestDto, MultipartFile file);

    void update(Long id, CatalogUpdateRequestDto requestDto);

    void delete(Long id);
}
