package com.example.jwtexample.usermanagment.auth;

import com.example.jwtexample.usermanagment.auth.dto.AuthenticationResponse;
import com.example.jwtexample.usermanagment.auth.dto.RefreshTokenRequest;
import com.example.jwtexample.usermanagment.auth.dto.RegisterRequestDto;
import com.example.jwtexample.usermanagment.user.UserService;
import com.example.jwtexample.usermanagment.user.dto.ApiResponse;
import com.example.jwtexample.usermanagment.user.dto.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Override
    public ResponseEntity<AuthenticationResponse> login(LoginRequest request) {
        return ResponseEntity.ok(authenticationService.authenticateLogin(request));
    }

    @Override
    public ResponseEntity<AuthenticationResponse> refreshToken(RefreshTokenRequest request, String authHeader) {
        String oldAccessToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            oldAccessToken = authHeader.substring(7);
        }

        return ResponseEntity.ok(
                authenticationService.refreshToken(request, oldAccessToken)
        );
    }

    @Override
    public ResponseEntity<ApiResponse<Void>> logout(String authHeader, String refreshToken) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse<>(false, "Authorization header is missing or invalid.", null));
        }

        String accessToken = authHeader.substring(7);

        try {
            authenticationService.logout(accessToken, refreshToken);
            return ResponseEntity.ok(new ApiResponse<>(true, "Logged out", null));
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(false, "Logged failed: " + e.getMessage(), null));
        }
    }

    @Override
    public ResponseEntity<AuthenticationResponse> createUser(@Valid RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(userService.register(registerRequestDto));
    }
}
