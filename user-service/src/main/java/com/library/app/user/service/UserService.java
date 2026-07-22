package com.library.app.user.service;

import com.library.app.user.config.AdminProperties;
import com.library.app.user.event.PasswordResetEvent;
import com.library.app.user.exceptions.TokenException;
import com.library.app.user.exceptions.UserException;
import com.library.app.user.model.PasswordResetToken;
import com.library.app.user.model.Role;
import com.library.app.user.model.User;
import com.library.app.user.repository.PasswordResetTokenRepository;
import com.library.app.user.repository.UserRepository;
import com.library.app.user.service.mapper.UserMapper;
import com.library.app.user.web.dto.AuthResponse;
import com.library.app.user.web.dto.KeycloakUserRequest;
import com.library.app.user.web.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AdminProperties adminProperties;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RestTemplate restTemplate;

    @Value("${keycloak.admin.token-uri}")
    private String keycloakTokenUri;

    @Value("${keycloak.admin.users-uri}")
    private String keycloakUsersUri;

    @Value("${keycloak.admin.client-id}")
    private String keycloakAdminClientId;

    @Value("${keycloak.admin.client-secret}")
    private String keycloakAdminClientSecret;

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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public AuthResponse register(UserRequest userRequest) {
        if (existsByEmail(userRequest.email())) {
            throw new UserException("User with such email already exists");
        }

        boolean isAdmin = adminProperties.getAdminEmails().contains(userRequest.email());
        String keycloakUserId = createKeycloakUser(userRequest, isAdmin ? "ADMIN" : "USER");

        User createdUser = isAdmin
                ? userMapper.mapAdmin(userRequest, keycloakUserId)
                : userMapper.getUser(userRequest, keycloakUserId);

        saveUser(createdUser);

        log.info("User registered: {}", createdUser.getEmail());

        return new AuthResponse(
                null,
                "You registered successfully.",
                String.format("Welcome %s!", createdUser.getFullName()),
                userMapper.getUserResponse(createdUser));
    }

//    @Transactional
//    public void createPasswordResetToken(String email) {
//        User user = findByEmail(email);
//
//        String frontendUrl = "http://localhost:5173/reset-password?token=";
//
//        String token = UUID.randomUUID().toString();
//        PasswordResetToken passwordResetToken = PasswordResetToken.builder()
//                .user(user)
//                .expiryDate(LocalDateTime.now().plusMinutes(5))
//                .token(token)
//                .build();
//
//        passwordResetTokenRepository.save(passwordResetToken);
//        String resetLink=frontendUrl+token;
//
//        PasswordResetEvent event = new PasswordResetEvent(email, resetLink);
//
//        kafkaTemplate.send("password-reset-events", event);
//        log.info("Password reset token created for user: {}", email);
//
//    }
//
//    @Transactional
//    public void resetPassword(String token, String newPassword) {
//        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
//                .orElseThrow(() -> new TokenException("Token not valid"));
//
//        if (passwordResetToken.isExpired()) {
//            passwordResetTokenRepository.delete(passwordResetToken);
//            throw new TokenException("Token has expired");
//        }
//
//        User user = passwordResetToken.getUser();
//        String encodedPassword = passwordEncoder.encode(newPassword);
//        user.setPassword(encodedPassword);
//        saveUser(user);
//        log.info("Password reset for user: {}", user.getEmail());
//    }

    private String getServiceAccountToken() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", keycloakAdminClientId);
        map.add("client_secret", keycloakAdminClientSecret);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                keycloakTokenUri,
                HttpMethod.POST,
                entity,
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        if (response.getBody() == null || !response.getBody().containsKey("access_token")) {
            throw new UserException("Failed to obtain service account token from Keycloak");
        }

        return (String) response.getBody().get("access_token");
    }

    private String createKeycloakUser(UserRequest userRequest,  String roleName) {
        String serviceToken = getServiceAccountToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(serviceToken);

        KeycloakUserRequest kcUser = KeycloakUserRequest.of(
                userRequest.email(),
                userRequest.email(),
                userRequest.fullName(),
                userRequest.phone(),
                userRequest.profileImage(),
                userRequest.password()
        );

        HttpEntity<KeycloakUserRequest> entity = new HttpEntity<>(kcUser, headers);

        ResponseEntity<Void> response;
        try {
            response = restTemplate.exchange(keycloakUsersUri, HttpMethod.POST, entity, Void.class);
        } catch (HttpClientErrorException.Conflict e) {
            throw new UserException("A user with this email already exists in the identity provider");
        } catch (Exception e) {
            log.error("Failed to create Keycloak user for email: {}", userRequest.email(), e);
            throw new UserException("Identity provider unavailable");
        }

        String location = response.getHeaders().getFirst(HttpHeaders.LOCATION);
        if (location == null) {
            throw new UserException("Keycloak did not return a user location");
        }
        String keycloakUserId = location.substring(location.lastIndexOf('/') + 1);

        assignRealmRole(keycloakUserId, roleName, serviceToken);

        return keycloakUserId;
    }

    private void assignRealmRole(String keycloakUserId, String roleName, String serviceToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(serviceToken);

        ResponseEntity<Map<String, Object>> roleResponse = restTemplate.exchange(
                keycloakUsersUri.replace("/users", "/roles/" + roleName),
                HttpMethod.GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<Map<String, Object>>() {}
        );

        if (roleResponse.getBody() == null) {
            throw new UserException("Realm role not found: " + roleName);
        }

        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Map<String, Object>>> assignEntity =
                new HttpEntity<>(List.of(roleResponse.getBody()), headers);

        String assignUri = keycloakUsersUri + "/" + keycloakUserId + "/role-mappings/realm";
        restTemplate.exchange(assignUri, HttpMethod.POST, assignEntity, Void.class);
    }


}
