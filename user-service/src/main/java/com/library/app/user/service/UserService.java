package com.library.app.user.service;

import com.library.app.user.exceptions.UserException;
import com.library.app.user.model.User;
import com.library.app.user.repository.UserRepository;
import com.library.app.user.web.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


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
}
