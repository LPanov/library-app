package com.library.app.user.web.dto;

import com.library.app.user.model.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        String password,
        String phone,
        String fullName,
        Role role,
        String username
) {
}
