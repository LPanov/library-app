package com.library.app.user.web.dto;

public record AuthResponse(
        String jwt,
        String message,
        String title,
        UserResponse user
) {
}
