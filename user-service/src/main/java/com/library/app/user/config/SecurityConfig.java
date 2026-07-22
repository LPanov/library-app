package com.library.app.user.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_JWK_SET_URI:${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}}")
    private String jwkSetUri;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/v1/user/register", "/api/v1/user/forgot-password", "/api/v1/user/reset-password", "/error").permitAll()
                    .anyRequest().authenticated()
            )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                        .decoder(jwtDecoder())
                        .jwtAuthenticationConverter(jwtAuthenticationConverter())));

        return http.build();
    }

    @Value("${SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI:${spring.security.oauth2.resourceserver.jwt.issuer-uri}}")
    private String issuerUri;

    @Bean
    public JwtDecoder jwtDecoder() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setConnectTimeout(3000);
        requestFactory.setReadTimeout(3000);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri)
                .restOperations(restTemplate)
                .cache(new ConcurrentMapCache("jwk-set"))
                .build();

        OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
        jwtDecoder.setJwtValidator(withIssuer);

        return jwtDecoder;
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            var realmAccess = jwt.getClaimAsMap("realm_access");
            if (realmAccess == null) return List.of();
            @SuppressWarnings("unchecked")
            var roles = (List<String>) realmAccess.get("roles");
            return roles.stream()
                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r.toUpperCase()))
                    .collect(Collectors.toList());
        });
        return converter;
    }
}