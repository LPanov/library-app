package com.library.app.user.service;

import com.library.app.user.domain.AuthProvider;
import com.library.app.user.exceptions.UserException;
import com.library.app.user.model.Role;
import com.library.app.user.model.User;
import com.library.app.user.repository.UserRepository;
import com.library.app.user.service.mapper.UserMapper;
import com.library.app.user.web.dto.AuthResponse;
import com.library.app.user.web.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public AuthResponse login(String username, String password) {
        return null;
    }

    public AuthResponse register(UserRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new UserException("User with such email already exists");
        }

        String encodedPassword = passwordEncoder.encode(userRequest.password());

        User savedUser = userMapper.getUser(userRequest, encodedPassword);

        userRepository.save(savedUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            savedUser.getEmail(), savedUser.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return null;
    }



    public void createPasswordResetToken(String email) {

    }

    public void resetPassword(String token, String newPassword) {

    }

}
