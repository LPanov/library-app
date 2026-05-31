package com.library.app.user.web.dto;

import com.library.app.user.annotations.Password;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record UserRequest(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @Password
        String password,

        @Email(message = "Invalid email format")
        String email,

        String fullName,
        String phone,
        String profileImage
) {
}
