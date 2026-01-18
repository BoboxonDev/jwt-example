package com.example.jwtexample.usermanagment.security;

import com.example.jwtexample.usermanagment.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser {

    public static Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof CustomUserDetails customUserDetails) {
            return customUserDetails.getId();
        }

        return null;
    }

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return authentication.getName();
    }

    public static String getFullName(UserRepository userRepository) {
        String username = getUsername();
        if (username == null) return null;

        return userRepository.findByUsernameAndDeletedAtIsNull(username)
            .map(u -> (u.getFirstName() + " " + u.getLastName()).trim())
            .orElse(null);
    }
}
