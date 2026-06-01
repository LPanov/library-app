package com.library.app.user.web;

import com.library.app.user.service.AuthService;
import com.library.app.user.web.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authService.register(userRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest.username(), loginRequest.password()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
        authService.createPasswordResetToken(forgotPasswordRequest.email());

        ApiResponse apiResponse = new ApiResponse(String.format("Reset Password Token sent successfully to %s", forgotPasswordRequest.email()), true);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
        authService.resetPassword(resetPasswordRequest.token(), resetPasswordRequest.password());

        return ResponseEntity.ok(new ApiResponse("Password reset successfully", true));

    }
}
