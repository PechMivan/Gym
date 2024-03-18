package com.gym.gym;

import com.gym.gym.controllers.TrainerController;
import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.CredentialsAndAccessToken;
import com.gym.gym.dtos.request.TrainerRegistrateRequest;
import com.gym.gym.dtos.request.TrainerUpdateRequest;
import com.gym.gym.dtos.response.TrainerFindResponse;
import com.gym.gym.dtos.response.TrainerUpdateResponse;
import com.gym.gym.entities.Token;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.User;
import com.gym.gym.mappers.TrainerMapper;
import com.gym.gym.services.implementations.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

public class TrainerControllerTests {

    @Mock
    TrainerServiceImpl trainerService;

    @Mock
    TrainerMapper trainerMapper;

    @InjectMocks
    TrainerController trainerController;

    String username;
    Trainer trainer;

    Credentials credentials;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        this.username = "username";

        this.trainer = new Trainer();
        this.credentials = new Credentials();

    }

    @Test
    public void getTrainerByUsername() {
        // Arrange
        TrainerFindResponse response = new TrainerFindResponse();
        when(trainerService.getTrainerByUsername(username)).thenReturn(trainer);
        when(trainerMapper.mapToFindResponse(trainer)).thenReturn(response);

        // Act
        ResponseEntity<TrainerFindResponse> responseEntity = trainerController.getTrainerByUsername(username);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());

        verify(trainerService, times(1)).getTrainerByUsername(username);
        verify(trainerMapper, times(1)).mapToFindResponse(trainer);
    }

    @Test
    public void createTrainer() {
        // Arrange
        TrainerRegistrateRequest request = new TrainerRegistrateRequest();
        Trainer newTrainer = new Trainer();
        User user = new User();
        Token accessToken = new Token();

        accessToken.setToken("tokenString");
        user.setTokens(Collections.singletonList(accessToken));
        user.setUsername(username);
        user.setPassword("password");
        newTrainer.setUser(user);

        when(trainerMapper.mapFromRegistrateRequest(request)).thenReturn(trainer);
        when(trainerService.createTrainer(trainer)).thenReturn(newTrainer);

        // Act
        ResponseEntity<CredentialsAndAccessToken> responseEntity = trainerController.createTrainer(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertInstanceOf(CredentialsAndAccessToken.class, responseEntity.getBody());
        assertEquals(accessToken.getToken(), responseEntity.getBody().getAccessToken());
        assertEquals(user.getUsername(), responseEntity.getBody().getCredentials().getUsername());
        assertEquals(user.getPassword(), responseEntity.getBody().getCredentials().getPassword());

        verify(trainerMapper, times(1)).mapFromRegistrateRequest(request);
        verify(trainerService, times(1)).createTrainer(trainer);
    }

    @Test
    public void updateTrainer() {
        // Arrange
        TrainerUpdateRequest request = new TrainerUpdateRequest();
        Trainer updatedTrainer = new Trainer();
        TrainerUpdateResponse response = new TrainerUpdateResponse();
        when(trainerMapper.mapFromUpdateRequest(request)).thenReturn(trainer);
        when(trainerService.updateTrainer(username, trainer)).thenReturn(updatedTrainer);
        when(trainerMapper.mapToUpdateResponse(updatedTrainer)).thenReturn(response);

        // Act
        ResponseEntity<TrainerUpdateResponse> responseEntity = trainerController.updateTrainer(username, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());

        verify(trainerMapper, times(1)).mapFromUpdateRequest(request);
        verify(trainerService, times(1)).updateTrainer(username, trainer);
        verify(trainerMapper, times(1)).mapToUpdateResponse(updatedTrainer);
    }
}
