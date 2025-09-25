package com.example.jwtexample.usermanagment.auth;

import com.example.jwtexample.usermanagment.auth.dto.AuthenticationResponse;
import com.example.jwtexample.usermanagment.auth.dto.RefreshTokenRequest;
import com.example.jwtexample.usermanagment.user.dto.LoginRequest;

public interface AuthenticationService {

    AuthenticationResponse authenticateLogin(LoginRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest request, String oldAccessToken);

    void logout(String accessToken, String refreshToken);

    AuthenticationResponse authenticateAdmin(LoginRequest request);
}
