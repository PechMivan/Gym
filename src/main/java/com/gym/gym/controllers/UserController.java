package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.services.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gym/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<HttpStatus> login(@RequestBody Credentials credentials){
        userService.authenticateUser(credentials.username, credentials.password);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
