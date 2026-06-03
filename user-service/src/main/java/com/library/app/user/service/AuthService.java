package com.library.app.user.service;

import com.library.app.user.config.AdminProperties;
import com.library.app.user.config.JwtProvider;
import com.library.app.user.event.PasswordResetEvent;
import com.library.app.user.exceptions.TokenException;
import com.library.app.user.exceptions.UserException;
import com.library.app.user.model.PasswordResetToken;
import com.library.app.user.model.User;
import com.library.app.user.repository.PasswordResetTokenRepository;
import com.library.app.user.service.mapper.UserMapper;
import com.library.app.user.web.dto.AuthResponse;
import com.library.app.user.web.dto.UserRequest;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AdminProperties adminProperties;

    public AuthResponse login(String username, String password) {
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        String role = authorities.iterator().next().getAuthority();

        String token = jwtProvider.generateToken(authentication);

        User user = userService.findByUsername(username);

        user.setLastLogin(LocalDateTime.now());
        userService.saveUser(user);

        return new AuthResponse(
                token,
                String.format("Welcome back %s!", username),
                "Login Success",
                userMapper.getUserResponse(user)
        );
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = userService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new UserException(String.format("User not found with username %s", username));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new UserException(String.format("Password for user %s is incorrect.", username));
        }

        return new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
    }

    @Transactional
    public AuthResponse register(UserRequest userRequest) {
        if (userService.existsByEmail(userRequest.email())) {
            throw new UserException("User with such email already exists");
        }

        String encodedPassword = passwordEncoder.encode(userRequest.password());

        User createdUser = adminProperties
                        .getAdminEmails()
                        .contains(userRequest.email()) ?
                        userMapper.mapAdmin(userRequest, encodedPassword) :
                        userMapper.getUser(userRequest, encodedPassword);

        userService.saveUser(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
            createdUser.getEmail(), createdUser.getPassword()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateToken(authentication);

        return new AuthResponse(
                jwt,
                "You registered successfully.",
                String.format("Welcome %s!", createdUser.getFullName()),
                userMapper.getUserResponse(createdUser));
    }


    @Transactional
    public void createPasswordResetToken(String email) {
        User user = userService.findByUsername(email);

        String frontendUrl = "http://localhost:5173/reset-password?token=";

        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
                .user(user)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .token(token)
                .build();

        passwordResetTokenRepository.save(passwordResetToken);
        String resetLink=frontendUrl+token;

        PasswordResetEvent event = new PasswordResetEvent(email, resetLink);

        kafkaTemplate.send("password-reset-events", event);

    }

    @Transactional
    public void resetPassword(String token, String newPassword) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenException("Token not valid"));

        if (passwordResetToken.isExpired()) {
            passwordResetTokenRepository.delete(passwordResetToken);
            throw new TokenException("Token has expired");
        }

        User user = passwordResetToken.getUser();
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userService.saveUser(user);
    }

}
