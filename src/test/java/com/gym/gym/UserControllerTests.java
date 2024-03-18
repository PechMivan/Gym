package com.gym.gym;
import com.gym.gym.controllers.UserController;
import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.ActiveStateChangeRequest;
import com.gym.gym.dtos.request.PasswordChangeRequest;
import com.gym.gym.entities.User;
import com.gym.gym.security.UserPrincipal;
import com.gym.gym.services.TokenService;
import com.gym.gym.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTests {

    @Mock
    UserServiceImpl userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    UserController userController;

    Credentials credentials;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        this.credentials = new Credentials("username", "password");
    }

    @Test
    public void testLogin_Success() {
        // Given
        Credentials credentials = new Credentials("test_username", "test_password");
        Authentication authentication = mock(Authentication.class);
        UserPrincipal principal = UserPrincipal.builder().userId(1L).username("username").build();
        String jwtToken = "jwt_token";

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(principal);
        when(tokenService.generateToken(principal.getUserId(), principal.getUsername(), List.of("USER"))).thenReturn(jwtToken);

        // When
        ResponseEntity<String> response = userController.login(credentials);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jwtToken, response.getBody());
    }

    @Test
    public void changeActiveState() {
        // Arrange
        ActiveStateChangeRequest request = new ActiveStateChangeRequest();

        // Act
        ResponseEntity<HttpStatus> responseEntity = userController.changeActiveState(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(userService, times(1)).changeActiveState(request.getUsername(), request.isActive());
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
