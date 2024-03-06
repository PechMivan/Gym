package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.ActiveStateChangeRequest;
import com.gym.gym.dtos.request.PasswordChangeRequest;
import com.gym.gym.entities.User;
import com.gym.gym.security.UserPrincipal;
import com.gym.gym.services.TokenService;
import com.gym.gym.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@Validated
@SuppressWarnings("unused")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    TokenService tokenService;

    //TODO: Rework login
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid Credentials credentials){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.username, credentials.password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();
        String jwtToken = tokenService.generateToken(principal.getUserId(), principal.getUsername(), List.of("USER"));
        User user = User.builder().id(principal.getUserId()).username(principal.getUsername()).build();
        tokenService.createToken(user, jwtToken);
        return new ResponseEntity<>(jwtToken, HttpStatus.OK);
    }

    @PatchMapping("/active")
    public ResponseEntity<HttpStatus> changeActiveState(@RequestBody @Valid ActiveStateChangeRequest request){
        Boolean activeState = userService.changeActiveState(request.username, request.active, request.credentials);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody @Valid PasswordChangeRequest request){
        boolean passwordChanged = userService.changePassword(request.username, request.oldPassword, request.newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
