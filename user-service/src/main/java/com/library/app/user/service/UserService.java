package com.library.app.user.service;

import com.library.app.user.exceptions.UserException;
import com.library.app.user.model.User;
import com.library.app.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    private User finByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = finByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new AuthenticationDetails(
                user.getId(), user.getEmail(), user.getPassword(), user.getRole(), user.getIsActive()
        );
    }
}
