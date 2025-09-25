package com.example.jwtexample.usermanagment.user;

import com.example.jwtexample.usermanagment.auth.dto.AuthenticationResponse;
import com.example.jwtexample.usermanagment.auth.dto.RegisterRequestDto;
import com.example.jwtexample.usermanagment.user.dto.ChangePasswordRequestDto;
import com.example.jwtexample.usermanagment.user.dto.UserRequestDto;
import com.example.jwtexample.usermanagment.user.dto.UserResponseDto;
import com.example.jwtexample.usermanagment.user.dto.UserUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    AuthenticationResponse register(RegisterRequestDto registerRequestDto);

    UserEntity get(Long userId);

    UserResponseDto updateUser(Long userId, UserUpdateRequestDto updateRequestDto);

    UserResponseDto getUser();

    void changePassword(Long userId, ChangePasswordRequestDto requestDto);

    void deleteUser(Long userId);

    void createUser(UserRequestDto userRequestDto);

    UserResponseDto getById(Long Id);

    Page<UserResponseDto> getAllUsers(UserFilterParams filterParams, Pageable pageable);
}
