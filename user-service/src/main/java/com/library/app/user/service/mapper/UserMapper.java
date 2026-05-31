package com.library.app.user.service.mapper;

import com.library.app.user.domain.AuthProvider;
import com.library.app.user.model.Role;
import com.library.app.user.model.User;
import com.library.app.user.web.dto.UserRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {
    public User getUser(UserRequest userRequest, String encodedPassword) {
        return User.builder()
                .email(userRequest.email())
                .username(userRequest.username())
                .password(encodedPassword)
                .fullName(userRequest.fullName())
                .phone(userRequest.phone())
                .profileImage(userRequest.profileImage())
                .role(Role.USER)
                .authProvider(AuthProvider.LOCAL)
                .isActive(true)
                .lastLogin(LocalDateTime.now())
                .build();
    }
}
