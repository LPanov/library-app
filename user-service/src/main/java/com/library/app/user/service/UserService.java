package com.library.app.user.service;

import com.library.app.user.config.AdminProperties;
import com.library.app.user.event.PasswordResetEvent;
import com.library.app.user.exceptions.TokenException;
import com.library.app.user.exceptions.UserException;
import com.library.app.user.model.PasswordResetToken;
import com.library.app.user.model.User;
import com.library.app.user.repository.PasswordResetTokenRepository;
import com.library.app.user.repository.UserRepository;
import com.library.app.user.service.mapper.UserMapper;
import com.library.app.user.web.dto.AuthResponse;
import com.library.app.user.web.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final AdminProperties adminProperties;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;


    public User findByUsername(String username) {
       return userRepository.findByUsername(username).orElseThrow(() -> new UserException("User not found"));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new AuthenticationDetails(
                user.getId(), user.getEmail(), user.getPassword(), user.getRole(), user.getIsActive()
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public AuthResponse login(String username, String password) {

        User user = findByUsername(username);

        user.setLastLogin(LocalDateTime.now());
        saveUser(user);

        log.info("User logged in: {}", username);

        return new AuthResponse(
                "token",
                String.format("Welcome back %s!", username),
                "Login Success",
                userMapper.getUserResponse(user)
        );
    }

    @Transactional
    public AuthResponse register(UserRequest userRequest) {
        if (existsByEmail(userRequest.email())) {
            throw new UserException("User with such email already exists");
        }

        String encodedPassword = passwordEncoder.encode(userRequest.password());

        User createdUser = adminProperties
                .getAdminEmails()
                .contains(userRequest.email()) ?
                userMapper.mapAdmin(userRequest, encodedPassword) :
                userMapper.getUser(userRequest, encodedPassword);

        saveUser(createdUser);

        log.info("User registered: {}", createdUser.getEmail());

        return new AuthResponse(
                "token",
                "You registered successfully.",
                String.format("Welcome %s!", createdUser.getFullName()),
                userMapper.getUserResponse(createdUser));
    }

    @Transactional
    public void createPasswordResetToken(String email) {
        User user = findByEmail(email);

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
        log.info("Password reset token created for user: {}", email);

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
        saveUser(user);
        log.info("Password reset for user: {}", user.getEmail());
    }


}
