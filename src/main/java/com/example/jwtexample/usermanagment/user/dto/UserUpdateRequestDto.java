package com.example.jwtexample.usermanagment.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequestDto {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private LocalDate birtOfDate;

    private String phoneNumber;
}
