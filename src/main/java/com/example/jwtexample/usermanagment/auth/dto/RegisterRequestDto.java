package com.example.jwtexample.usermanagment.auth.dto;

import com.example.jwtexample.enums.Salutation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequestDto {

    @NotBlank
    private String username;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    private Salutation salutation;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private LocalDate dateOfBirth;

    @Pattern(regexp = "[0-9]{9,15}$", message = "Phone number must be between 9 and 15 digits")
    private String phoneNumber;
}
