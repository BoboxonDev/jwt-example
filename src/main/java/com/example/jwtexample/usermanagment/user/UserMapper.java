package com.example.jwtexample.usermanagment.user;

import com.example.jwtexample.usermanagment.auth.dto.RegisterRequestDto;
import com.example.jwtexample.usermanagment.user.dto.UserRequestDto;
import com.example.jwtexample.usermanagment.user.dto.UserResponseDto;
import com.example.jwtexample.usermanagment.user.dto.UserUpdateRequestDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface UserMapper {

    UserEntity toEntity(RegisterRequestDto requestDto);

    UserEntity toEntityAdmin(UserRequestDto requestDto);

    UserResponseDto toDto(UserEntity entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(UserUpdateRequestDto requestDto, @MappingTarget UserEntity user);
}
