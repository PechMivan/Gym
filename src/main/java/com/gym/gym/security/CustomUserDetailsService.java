package com.gym.gym.security;

import com.gym.gym.entities.User;
import com.gym.gym.exceptions.customExceptions.MaxLoginAttemptsException;
import com.gym.gym.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;
    private final LoginAttemptService loginAttemptService;
    private final HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        if (loginAttemptService.isBlocked()) {
            //TODO: Handle this exception
            throw new MaxLoginAttemptsException("Max number of login attempts reached. Try again in 5 minutes");
        }

        User user = userService.getUserByUsername(username);

        return UserPrincipal.builder()
                            .userId(user.getId())
                            .username(user.getUsername())
                            .authorities(List.of(new SimpleGrantedAuthority("USER")))
                            .password(user.getHashedPassword())
                            .build();
    }
}
