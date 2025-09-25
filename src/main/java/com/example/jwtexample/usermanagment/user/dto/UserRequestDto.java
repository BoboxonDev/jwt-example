package com.example.jwtexample.usermanagment.user.dto;

import com.example.jwtexample.enums.ERole;
import com.example.jwtexample.enums.Salutation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private ERole role;

    private Salutation salutation;

    @NotBlank
    private String password;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private LocalDate dateOfBirth;

    private String phoneNumber;
}
