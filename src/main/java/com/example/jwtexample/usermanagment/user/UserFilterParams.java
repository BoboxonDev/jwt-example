package com.example.jwtexample.usermanagment.user;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserFilterParams {

    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private LocalDate dateOfBirth;
}
