package com.library.app.user.service;

import com.library.app.user.web.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserInit implements CommandLineRunner {

    private final UserService userService;
    private final AuthService authService;

    @Override
    public void run(String... args) throws Exception {
        if (!userService.getAllUsers().isEmpty()) {
            return;
        }

        UserRequest registerRequest = new UserRequest(
                "admin",
                "admin123",
                "admin_admin@mail.com",
                "Admin Admin",
                null,
                null
        );

        authService.register(registerRequest);
    }
}
