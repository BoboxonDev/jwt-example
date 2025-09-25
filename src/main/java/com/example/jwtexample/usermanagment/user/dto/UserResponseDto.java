package com.example.jwtexample.usermanagment.user.dto;

import com.example.jwtexample.enums.Salutation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserResponseDto {

    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private LocalDate dateOfBirth;

    private Salutation salutation;

    public UserResponseDto(
            Long id,
            String username,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            LocalDate dateOfBirth,
            Salutation salutation
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.salutation = salutation;
    }
}
