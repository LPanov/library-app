package com.library.app.user.service.mapper;

import com.library.app.user.domain.AuthProvider;
import com.library.app.user.model.Role;
import com.library.app.user.model.User;
import com.library.app.user.web.dto.UserRequest;
import com.library.app.user.web.dto.UserResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    public User getUser(UserRequest userRequest, String keycloakId) {
        return User.builder()
                .email(userRequest.email())
                .username(userRequest.username())
                .keycloakId(keycloakId)
                .fullName(userRequest.fullName())
                .phone(userRequest.phone())
                .profileImage(userRequest.profileImage())
                .role(Role.USER)
                .authProvider(AuthProvider.KEYCLOAK)
                .isActive(true)
                .lastLogin(LocalDateTime.now())
                .build();
    }

    public User mapAdmin(UserRequest userRequest, String keycloakId) {
        return User.builder()
                .email(userRequest.email())
                .username(userRequest.username())
                .keycloakId(keycloakId)
                .fullName(userRequest.fullName())
                .phone(userRequest.phone())
                .profileImage(userRequest.profileImage())
                .role(Role.ADMIN)
                .authProvider(AuthProvider.KEYCLOAK)
                .isActive(true)
                .lastLogin(LocalDateTime.now())
                .build();
    }

    public UserResponse getUserResponse(User createdUser) {
        return new UserResponse(
                createdUser.getId(),
                createdUser.getEmail(),
                createdUser.getKeycloakId(),
                createdUser.getPhone(),
                createdUser.getFullName(),
                createdUser.getRole(),
                createdUser.getUsername()
        );
    }
}
