package com.gym.gym.security;

import com.gym.gym.entities.User;
import com.gym.gym.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws RuntimeException {
        if (loginAttemptService.isBlocked()) {
            throw new RuntimeException("blocked");
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
