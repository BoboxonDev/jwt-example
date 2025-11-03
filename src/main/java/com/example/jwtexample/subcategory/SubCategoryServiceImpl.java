package com.example.jwtexample.subcategory;

import com.example.jwtexample.common.exception.ResourceNotFoundException;
import com.example.jwtexample.subcategory.dto.SubCategoryRequestDto;
import com.example.jwtexample.subcategory.dto.SubCategoryResponseDto;
import com.example.jwtexample.subcategory.dto.SubCategoryUpdateRequestDto;
import com.example.jwtexample.usermanagment.security.CurrentUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final SubCategoryRepository repository;
    private final SubCategoryMapper mapper;

    @Override
    public Page<SubCategoryResponseDto> getAll(SubCategoryFilterParams filterParams, Pageable pageable) {
        return repository.findAllSubCategories(filterParams, pageable);
    }

    @Override
    public SubCategoryResponseDto getById(Long id) {
        var entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SubCategory not found"));
        return mapper.toDto(entity);
    }

    @Override
    public SubCategoryEntity get(Long id) {
        return repository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> {
                    log.error("SubCategory not found with provided ID={}", id);
                    return new ResourceNotFoundException("SubCategory not found with ID: " + id);
                });
    }

    @Override
    public void create(SubCategoryRequestDto requestDto) {
        var entity = mapper.toEntity(requestDto);
        repository.save(entity);
    }

    @Override
    public void update(Long id, SubCategoryUpdateRequestDto requestDto) {
        var entity = get(id);
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
