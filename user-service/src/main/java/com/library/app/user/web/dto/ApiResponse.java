package com.library.app.user.web.dto;

public record ApiResponse(
        String message,
        Boolean status
) {
}
