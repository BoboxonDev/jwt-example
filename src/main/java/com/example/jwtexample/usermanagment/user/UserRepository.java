package com.example.jwtexample.usermanagment.user;

import com.example.jwtexample.usermanagment.user.dto.UserResponseDto;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameAndDeletedAtIsNull(@NotEmpty String username);

    boolean existsByEmailAndDeletedAtIsNull(String email);

    Optional<UserEntity> findByEmailAndDeletedAtIsNull(String email);

    Optional<UserEntity> findByIdAndDeletedAtIsNull(Long id);


    @Query("""
      select new com.example.jwtexample.usermanagment.user.dto.UserResponseDto(
      user.id,
      user.username,
      user.email,
      user.firstName,
      user.lastName,
      user.phoneNumber,
      user.dateOfBirth
      ) from UserEntity user
      left join user.roles role
      where user.deletedAt is null and role.id = :#{#roleId}
          and(:#{#filterParams.username} is null or user.username = :#{#filterParams.username})
          and(:#{#filterParams.email} is null or user.email = :#{#filterParams.email})
          and(:#{#filterParams.firstName} is null or user.firstName = :#{#filterParams.firstName})
          and(:#{#filterParams.lastName} is null or user.lastName = :#{#filterParams.lastName})
          and(:#{#filterParams.phoneNumber} is null or user.phoneNumber = :#{#filterParams.phoneNumber})
          and(:#{#filterParams.dateOfBirth} is null or user.dateOfBirth = :#{#filterParams.dateOfBirth})
""")
    Page<UserResponseDto> findAllUsers(UserFilterParams filterParams, Pageable pageable,
    Long userRoleId);
}
