package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.ActiveStateChangeRequest;
import com.gym.gym.dtos.request.PasswordChangeRequest;
import com.gym.gym.entities.User;
import com.gym.gym.security.UserPrincipal;
import com.gym.gym.services.TokenService;
import com.gym.gym.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("gym/user")
@RequiredArgsConstructor
@Validated
@SuppressWarnings("unused")
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final TokenService tokenService;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid Credentials credentials) {
        Authentication authentication = authenticateUser(credentials);
        setAuthenticationInSecurityContext(authentication);
        UserPrincipal principal = extractPrincipalFromAuthentication(authentication);
        String jwtToken = generateJwtToken(principal);
        saveTokenForUser(principal, jwtToken);
        return ResponseEntity.ok(jwtToken);
    }

    private Authentication authenticateUser(Credentials credentials) {
        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
        );
    }

    private void setAuthenticationInSecurityContext(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private UserPrincipal extractPrincipalFromAuthentication(Authentication authentication) {
        return (UserPrincipal) authentication.getPrincipal();
    }

    private String generateJwtToken(UserPrincipal principal) {
        return tokenService.generateToken(principal.getUserId(), principal.getUsername(), List.of("USER"));
    }

    private void saveTokenForUser(UserPrincipal principal, String jwtToken) {
        User user = User.builder().id(principal.getUserId()).username(principal.getUsername()).build();
        tokenService.createToken(user, jwtToken);
    }

    @PatchMapping("/active")
    public ResponseEntity<HttpStatus> changeActiveState(@RequestBody @Valid ActiveStateChangeRequest request){
        Boolean activeState = userService.changeActiveState(request.username, request.active);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody @Valid PasswordChangeRequest request){
        boolean passwordChanged = userService.changePassword(request.username, request.oldPassword, request.newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
