package com.example.jwtexample.usermanagment.user.dto;

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

    public UserResponseDto(
            Long id,
            String username,
            String email,
            String firstName,
            String lastName,
            String phoneNumber,
            LocalDate dateOfBirth
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }
}
