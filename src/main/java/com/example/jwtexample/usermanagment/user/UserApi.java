package com.example.jwtexample.usermanagment.user;

import com.example.jwtexample.usermanagment.user.dto.ChangePasswordRequestDto;
import com.example.jwtexample.usermanagment.user.dto.UserResponseDto;
import com.example.jwtexample.usermanagment.user.dto.UserUpdateRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "APIs for managing Users")
public interface UserApi {

    @GetMapping
    @Operation
    ResponseEntity<Page<UserResponseDto>> getAllUsers(UserFilterParams filterParams, Pageable pageable);

    @PatchMapping("/{userId}")
    @Operation(summary = "Update user info")
    ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto);


    @PutMapping("/{userId}/change-password")
    @Operation(summary = "Change password")
    ResponseEntity<Void> changePassword(
            @PathVariable Long userId,
            @Valid @RequestBody ChangePasswordRequestDto requestDto);


    @GetMapping("/me")
    @Operation(summary = "Get current user info")
    ResponseEntity<UserResponseDto> getCurrentUser();

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete en existing user")
    ResponseEntity<Void> deleteUser(@PathVariable Long userId);
}
