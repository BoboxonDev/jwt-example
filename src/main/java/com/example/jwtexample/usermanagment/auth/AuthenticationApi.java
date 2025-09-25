package com.example.jwtexample.usermanagment.auth;

import com.example.jwtexample.usermanagment.auth.dto.AuthenticationResponse;
import com.example.jwtexample.usermanagment.auth.dto.RefreshTokenRequest;
import com.example.jwtexample.usermanagment.auth.dto.RegisterRequestDto;
import com.example.jwtexample.usermanagment.user.dto.ApiResponse;
import com.example.jwtexample.usermanagment.user.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "APIs for managing authentication: login, register, token refresh and logout")
public interface AuthenticationApi {

    @PostMapping("/login")
    @Operation(summary = "Login with username and password")
    ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest request);

    @PostMapping("/refresh-token")
    @Operation(summary = "Refresh access token using a valid refresh token")
    ResponseEntity<AuthenticationResponse> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request,
            @RequestHeader(value = "Authorization", required = false) String authHeader
    );

    @PostMapping("/logout")
    @Operation(summary = "Logout the current user and blacklist tokens")
    ResponseEntity<ApiResponse<Void>> logout(
            @RequestHeader("Authorization") String authHeader,
            @RequestHeader(value = "Refresh-Token", required = false) String refreshToken
    );

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    ResponseEntity<AuthenticationResponse> createUser(@Valid @RequestBody RegisterRequestDto registerRequestDto);
}
