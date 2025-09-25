package com.example.jwtexample.usermanagment.auth;

import com.example.jwtexample.enums.ERole;
import com.example.jwtexample.usermanagment.auth.dto.AuthenticationResponse;
import com.example.jwtexample.usermanagment.auth.dto.RefreshTokenRequest;
import com.example.jwtexample.usermanagment.config.CustomUserDetailsService;
import com.example.jwtexample.usermanagment.security.JwtUtil;
import com.example.jwtexample.usermanagment.user.UserEntity;
import com.example.jwtexample.usermanagment.user.UserRepository;
import com.example.jwtexample.usermanagment.user.dto.LoginRequest;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final CustomUserDetailsService userDetailsService;

    private static final String REFRESH = "refresh";

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request, String oldAccessToken) {
        String refreshToken = request.getRefreshToken();

        try {
            String tokenType = jwtUtil.extractTokenType(refreshToken);
            if (!REFRESH.equals(tokenType)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Only refresh tokens are allowed");
            }

            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!jwtUtil.isTokenValid(refreshToken, userDetails)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token is invalid");
            }

            UserEntity user = userRepository.findByUsernameAndDeletedAtIsNull(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            Long tenantId = user.getTenantId();

            String newAccessToken = jwtUtil.generateToken(user, tenantId);
            String newRefreshToken = jwtUtil.generateRefreshToken(user, tenantId);

            return new AuthenticationResponse(
                    newAccessToken,
                    newRefreshToken,
                    user.getEmail(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUsername()
            );
        } catch (JwtException | UsernameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Refresh token is invalid or expired");
        }
    }

    @Override
    public AuthenticationResponse authenticateLogin(LoginRequest request) {
        UserEntity user = authenticateAndGetUser(request.getUsername(), request.getPassword());
        requireRole(user, ERole.USER);
        return buildResponse(user);
    }

    @Override
    public void logout(String accessToken, String refreshToken) {
        try {
            if (accessToken != null && !accessToken.isBlank()) {
                String accessJti = jwtUtil.extractTokenId(accessToken);
                log.info("Access token {} logged out", accessJti);
            }

            if (refreshToken != null && !refreshToken.isBlank()) {
                String refreshJti = jwtUtil.extractTokenId(refreshToken);
                log.info("Refresh token {} logged out", refreshJti);
            }
        } catch (Exception e) {
            log.warn("logout error: {}", e.getMessage());
        }
    }

    @Override
    public AuthenticationResponse authenticateAdmin(LoginRequest request) {
        UserEntity user = authenticateAndGetUser(request.getUsername(), request.getPassword());
        requireRole(user, ERole.ADMIN);
        return buildResponse(user);
    }

    private UserEntity authenticateAndGetUser(String username, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        return userRepository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private void requireRole(UserEntity user, ERole requireRole) {
        boolean hasRole = user.getRoles().stream()
                .anyMatch(role -> role.getName().equals(requireRole));
        if (!hasRole) {
            throw new AccountExpiredException("You are not authorized to access this resource.");
        }
    }

    private AuthenticationResponse buildResponse(UserEntity user) {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());

        Long tenantId = user.getTenantId();

        response.setAccessToken(jwtUtil.generateToken(user, tenantId));
        response.setRefreshToken(jwtUtil.generateRefreshToken(user, tenantId));

        return response;
    }
}
