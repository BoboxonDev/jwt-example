package com.example.jwtexample.companymanagement.period;

import com.example.jwtexample.companymanagement.period.dto.PeriodRequestDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodResponseDto;
import com.example.jwtexample.companymanagement.period.dto.PeriodUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/periods")
@Tag(name = "Periods")
public interface PeriodApi {

    @GetMapping
    @Operation(summary = "Get all Periods",
            description = "Retrieve a list of all periods stored in the system")
    ResponseEntity<Page<PeriodResponseDto>> getAllPeriods(PeriodFilterParams filterParams, Pageable pageable);

    @GetMapping("{id}")
    @Operation(summary = "Get Period by ID",
            description = "Retrieve the details of a specific period using its unique ID")
    ResponseEntity<PeriodResponseDto> getPeriodById(@PathVariable Long id);

    @PostMapping
    @Operation(summary = "Create Period",
            description = "Create a new period with the provided request body and save it to the system")
    ResponseEntity<PeriodResponseDto> create(@Valid @RequestBody PeriodRequestDto periodRequestDto);

    @PatchMapping("{id}")
    @Operation(summary = "Update Period",
            description = "Update an existing period partially using the provided ID and request body")
    ResponseEntity<PeriodResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody PeriodUpdateRequestDto periodRequestDto
    );

    @DeleteMapping("{id}")
    @Operation(summary = "Delete Period",
            description = "Delete an existing period from the system using its unique ID")
    ResponseEntity<Void> delete(@PathVariable Long id);
}
