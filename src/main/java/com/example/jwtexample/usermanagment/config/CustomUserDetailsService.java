package com.example.jwtexample.usermanagment.config;

import com.example.jwtexample.usermanagment.security.CustomUserDetails;
import com.example.jwtexample.usermanagment.user.UserEntity;
import com.example.jwtexample.usermanagment.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsernameAndDeletedAtIsNull(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return buildUserDetails(user);
    }

    public UserDetails loadUserByUsername(String username, Long tenantId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsernameAndDeletedAtIsNull(username)
                .filter(u -> u.getTenantId().equals(tenantId))
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found for username: " + username + " and tenantId: " + tenantId));

        return buildUserDetails(user);
    }

    private CustomUserDetails buildUserDetails(UserEntity user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(
                        permission.getResource().getName() + "." + permission.getAction().getName()
                ))
                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getTenantId(),
                authorities
        );
    }
}
