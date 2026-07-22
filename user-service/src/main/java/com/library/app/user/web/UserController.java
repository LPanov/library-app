package com.library.app.user.web;

import com.library.app.user.service.UserService;
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
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(userRequest));
    }

//    @PostMapping("/forgot-password")
//    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody @Valid ForgotPasswordRequest forgotPasswordRequest) {
//        userService.createPasswordResetToken(forgotPasswordRequest.email());
//
//        ApiResponse apiResponse = new ApiResponse(String.format("Reset Password Token sent successfully to %s", forgotPasswordRequest.email()), true);
//        return ResponseEntity.ok(apiResponse);
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPasswordRequest resetPasswordRequest) {
//        userService.resetPassword(resetPasswordRequest.token(), resetPasswordRequest.password());
//
//        return ResponseEntity.ok(new ApiResponse("Password reset successfully", true));
//
//    }
}
