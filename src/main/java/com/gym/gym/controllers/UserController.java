package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.ActiveStateChangeRequest;
import com.gym.gym.dtos.request.PasswordChangeRequest;
import com.gym.gym.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.gym.gym.security.JwtIssuer;

import java.util.List;

@RestController
@RequestMapping("gym/user")
@Validated
@SuppressWarnings("unused")
public class UserController {

    @Autowired
    JwtIssuer jwtIssuer;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServiceImpl userService;

    //TODO: Rework login
    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid Credentials credentials){
        String token = jwtIssuer.issue(1L, "username", List.of("USER"));
        //userService.authenticateUser(credentials.username, credentials.password);
        return new ResponseEntity<>(token, HttpStatus.OK);
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
