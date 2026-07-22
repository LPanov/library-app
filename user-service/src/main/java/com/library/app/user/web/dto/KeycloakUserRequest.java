package com.library.app.user.web.dto;

import java.util.List;
import java.util.Map;

public record KeycloakUserRequest(
        String username,
        String email,
        String fullName,
        String phone,
        String profileImage,
        boolean enabled,
        List<Map<String, Object>> credentials
) {
    public static KeycloakUserRequest of(String username, String email, String fullName, String phone, String profileImage, String password) {
        Map<String, Object> credential = Map.of(
                "type", "password",
                "value", password,
                "temporary", false
        );
        return new KeycloakUserRequest(username, email, fullName, phone, profileImage, true, List.of(credential));
    }
}