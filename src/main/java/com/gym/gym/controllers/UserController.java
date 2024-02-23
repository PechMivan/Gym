package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.ActiveStateChangeRequest;
import com.gym.gym.dtos.request.PasswordChangeRequest;
import com.gym.gym.services.implementations.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gym/user")
@Validated
@SuppressWarnings("unused")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody @Valid Credentials credentials){
        userService.authenticateUser(credentials.username, credentials.password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/active/change")
    public ResponseEntity<HttpStatus> changeActiveState(@RequestBody @Valid ActiveStateChangeRequest request){
        Boolean activeState = userService.changeActiveState(request.username, request.active, request.credentials);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/password")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody @Valid PasswordChangeRequest request){
        boolean passwordChanged = userService.changePassword(request.username, request.oldPassword, request.newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
