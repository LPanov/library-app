package com.library.app.user.web.dto;

import com.library.app.user.annotations.Password;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,
        @Password
        String password
) {

}
