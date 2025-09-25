package com.example.jwtexample.usermanagment.user;

import com.example.jwtexample.usermanagment.user.dto.ChangePasswordRequestDto;
import com.example.jwtexample.usermanagment.user.dto.UserResponseDto;
import com.example.jwtexample.usermanagment.user.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(UserFilterParams filterParams, Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(filterParams, pageable));
    }

    @Override
    public ResponseEntity<UserResponseDto> updateUser(Long userId, UserUpdateRequestDto userUpdateRequestDto) {
        UserResponseDto updatedUser = userService.updateUser(userId, userUpdateRequestDto);
        return ResponseEntity.ok(updatedUser);
    }

    @Override
    public ResponseEntity<Void> changePassword(Long userId, ChangePasswordRequestDto requestDto) {
        userService.changePassword(userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getUser());
    }

    @Override
    public ResponseEntity<Void> deleteUser(Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
