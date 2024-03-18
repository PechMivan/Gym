package com.gym.gym;
import com.gym.gym.controllers.UserController;
import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.ActiveStateChangeRequest;
import com.gym.gym.dtos.request.PasswordChangeRequest;
import com.gym.gym.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    UserController userController;

    Credentials credentials;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        this.credentials = new Credentials("username", "password");
    }

    @Test
    public void login() {
        // Act
        ResponseEntity<HttpStatus> responseEntity = userController.login(credentials);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).authenticateUser(credentials.getUsername(), credentials.getPassword());
    }

    @Test
    public void changeActiveState() {
        // Arrange
        ActiveStateChangeRequest request = new ActiveStateChangeRequest();

        // Act
        ResponseEntity<HttpStatus> responseEntity = userController.changeActiveState(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).changeActiveState(request.getUsername(), request.isActive(), request.getCredentials());
    }

    @Test
    public void testChangePassword() {
        // Arrange
        PasswordChangeRequest request = new PasswordChangeRequest();

        // Act
        ResponseEntity<HttpStatus> responseEntity = userController.changePassword(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        verify(userService, times(1)).changePassword(request.username, request.oldPassword, request.newPassword);
    }
}
