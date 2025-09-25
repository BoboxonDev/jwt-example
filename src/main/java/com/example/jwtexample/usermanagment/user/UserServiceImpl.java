package com.example.jwtexample.usermanagment.user;

import com.example.jwtexample.common.exception.DuplicateException;
import com.example.jwtexample.common.exception.PasswordMismatchException;
import com.example.jwtexample.common.exception.ResourceNotFoundException;
import com.example.jwtexample.enums.ERole;
import com.example.jwtexample.usermanagment.auth.dto.AuthenticationResponse;
import com.example.jwtexample.usermanagment.auth.dto.RegisterRequestDto;
import com.example.jwtexample.usermanagment.role.RoleRepository;
import com.example.jwtexample.usermanagment.security.CurrentUser;
import com.example.jwtexample.usermanagment.security.JwtUtil;
import com.example.jwtexample.usermanagment.user.dto.ChangePasswordRequestDto;
import com.example.jwtexample.usermanagment.user.dto.UserRequestDto;
import com.example.jwtexample.usermanagment.user.dto.UserResponseDto;
import com.example.jwtexample.usermanagment.user.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final JwtUtil jwtUtil;

    private static final long USER_ROLE_ID = 1;

    @Override
    public AuthenticationResponse register(RegisterRequestDto registerRequestDto) {

        String password = Optional.ofNullable(registerRequestDto.getPassword())
                .map(String::trim)
                .orElseThrow(() -> new PasswordMismatchException("Password cannot be null or blank"));

        String confirmPassword = Optional.ofNullable(registerRequestDto.getConfirmPassword())
                .map(String::trim)
                .orElseThrow(() -> new PasswordMismatchException("Confirm password cannot be null or blank"));

        if (!password.equals(confirmPassword)) {
            throw new PasswordMismatchException("Passwords do not match with each other!");
        }

        if (userRepository.findByUsernameAndDeletedAtIsNull(registerRequestDto.getUsername()).isPresent()) {
            throw new DuplicateException(
                    "Username already exists with this name: " + registerRequestDto.getUsername());
        }

        if (userRepository.existsByEmailAndDeletedAtIsNull(registerRequestDto.getEmail())) {
            throw new DuplicateException("Email already exists with this email: " + registerRequestDto.getEmail());
        }

        var user = userMapper.toEntity(registerRequestDto);
        user.setPassword(passwordEncoder.encode(password));

        var roleUser = roleRepository.findByNameAndDeletedAtIsNull(ERole.USER)
                .orElseThrow(() -> {
                    log.error("Default role {} not found in the database", ERole.USER);
                    return new ResourceNotFoundException("Default role ROLE_USER not found");
                });

        user.setRoles(new HashSet<>(List.of(roleUser)));

        userRepository.save(user);

        Long tenantId = user.getTenantId();
        String accessToken = jwtUtil.generateToken(user, tenantId);
        String refreshToken = jwtUtil.generateRefreshToken(user, tenantId);

        return new AuthenticationResponse(
                accessToken,
                refreshToken,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername()
        );
    }

    @Override
    public UserEntity get(Long userId) {
        return userRepository.findByIdAndDeletedAtIsNull(userId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("User with id " + userId + " not found"));
    }

    @Override
    public UserResponseDto updateUser(Long userId, UserUpdateRequestDto updateRequestDto) {
        var user = get(CurrentUser.getUserId());
        userMapper.updateEntityFromDto(updateRequestDto, user);

        return userMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto getUser() {
        var userId = CurrentUser.getUserId();
        var user = get(userId);

        return userMapper.toDto(user);
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequestDto requestDto) {
        var user = get(userId);

        user.setPassword(passwordEncoder.encode(requestDto.getNewPassword()));

        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long userId) {
        var user = get(userId);
        user.setDeletedAt(LocalDateTime.now());
        user.setDeletedBy(CurrentUser.getUserId());
        userRepository.save(user);
    }

    @Override
    public void createUser(UserRequestDto userRequestDto) {

        if (userRepository.existsByEmailAndDeletedAtIsNull(userRequestDto.getUsername())) {
            throw new DuplicateException("Username already exists with this email: " + userRequestDto.getUsername());
        }

        if (userRepository.existsByEmailAndDeletedAtIsNull(userRequestDto.getEmail())) {
            throw new DuplicateException("Email already exists with this email: " + userRequestDto.getEmail());
        }

        var user = userMapper.toEntityAdmin(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        ERole roleName = Optional.ofNullable(userRequestDto.getRole())
                .map(Enum::name)
                .map(ERole::valueOf)
                .orElseThrow(() -> new ResourceNotFoundException("Role is null or invalid"));

        var role = roleRepository.findByNameAndDeletedAtIsNull(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + roleName));

        user.setRoles(new HashSet<>(List.of(role)));

        userRepository.save(user);

        log.info("Created username '{}' with role '{}'", userRequestDto.getUsername(),
                userRequestDto.getRole().name());
    }

    @Override
    public UserResponseDto getById(Long Id) {
        var user = get(Id);

        return userMapper.toDto(user);
    }

    @Override
    public Page<UserResponseDto> getAllUsers(UserFilterParams filterParams, Pageable pageable) {
        return userRepository.findAllUsers(filterParams, pageable, USER_ROLE_ID);
    }
}
