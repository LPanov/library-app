package com.library.app.user.web.dto;

import com.library.app.user.annotations.Password;
import jakarta.validation.constraints.NotBlank;

public record ResetPasswordRequest(
        @Password
        String password,
        @NotBlank(message = "Token cannot be blank")
        String token
) {
}

