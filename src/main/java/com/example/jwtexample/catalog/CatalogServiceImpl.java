package com.example.jwtexample.catalog;

import com.example.jwtexample.catalog.dto.CatalogRequestDto;
import com.example.jwtexample.catalog.dto.CatalogResponseDto;
import com.example.jwtexample.catalog.dto.CatalogUpdateRequestDto;
import com.example.jwtexample.category.CategoryRepository;
import com.example.jwtexample.category.CategoryService;
import com.example.jwtexample.common.exception.ResourceNotFoundException;
import com.example.jwtexample.company.CompanyRepository;
import com.example.jwtexample.company.CompanyService;
import com.example.jwtexample.fileservice.FileService;
import com.example.jwtexample.fileservice.FileUploadResponseDto;
import com.example.jwtexample.subcategory.SubCategoryRepository;
import com.example.jwtexample.subcategory.SubCategoryService;
import com.example.jwtexample.usermanagment.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRepository repository;
    private final CatalogMapper mapper;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final SubCategoryService subCategoryService;
    private final SubCategoryRepository subCategoryRepository;
    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final FileService fileService; // ✅ monolitda shu ishlatiladi

    @Override
    public Page<CatalogResponseDto> getAll(CatalogFilterParams filterParams, Pageable pageable) {
        return repository.findAllCatalogs(filterParams, pageable);
    }

    @Override
    public List<CatalogResponseDto> getByUserId(Long userId) {
        var fileEntities = repository.findByUserIdAndDeletedAtIsNull(userId);
        return fileEntities.stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public CatalogEntity get(Long id) {
        var catalog = repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("Catalog not found with ID: " + id));

        var category = categoryRepository.findByIdAndDeletedAtIsNull(catalog.getCategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found : " + catalog.getCategory().getId()));
        catalog.setCategory(category);

        var subCategory = subCategoryRepository.findByIdAndDeletedAtIsNull(catalog.getSubcategory().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Sub Category not found: " + catalog.getSubcategory().getId()));
        catalog.setSubcategory(subCategory);

        var company = companyRepository.findByIdAndDeletedAtIsNull(catalog.getCompany().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found: " + catalog.getCompany().getId()));
        catalog.setCompany(company);

        return catalog;
    }

    @Override
    public CatalogResponseDto create(CatalogRequestDto dto, MultipartFile file) {
        // Faylni yuklash
        FileUploadResponseDto fileResponse = fileService.uploadFile(file, "catalogs");
        log.info("File uploaded: {}", fileResponse.getUrl());

        // ️ Entity’ni to‘ldirish
        CatalogEntity entity = new CatalogEntity();
        entity.setTitle(dto.getTitle());
        entity.setAddress(dto.getAddress());
        entity.setPhone(dto.getPhone());
        entity.setDescription(dto.getDescription());
        entity.setCategory(categoryService.get(dto.getCategoryId()));
        entity.setSubcategory(dto.getSubcategoryId() != null ? subCategoryService.get(dto.getSubcategoryId()) : null);
        entity.setCompany(companyService.getCompany(dto.getCompanyId()));

        // Auditing uchun
        entity.setCreatedAt(LocalDateTime.now());
        entity.setCreatedBy(CurrentUser.getUserId());

        // Saqlash
        var saved = repository.save(entity);
        log.info("Catalog created with id {}", saved.getId());

        // DTO qaytarish
        return mapper.toDto(saved);
    }

    @Override
    public void update(Long id, CatalogUpdateRequestDto requestDto) {
        var entity = get(id);

        Optional.ofNullable(requestDto.getCategoryId())
                .map(categoryService::get)
                .ifPresent(entity::setCategory);

        Optional.ofNullable(requestDto.getSubcategoryId())
                .map(subCategoryService::get)
                .ifPresent(entity::setSubcategory);

        Optional.ofNullable(requestDto.getCompanyId())
                .map(companyService::getCompany)
                .ifPresent(entity::setCompany);

        mapper.partialUpdate(entity, requestDto);
        repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        var entity = get(id);
        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(CurrentUser.getUserId());
        repository.save(entity);
    }
}
