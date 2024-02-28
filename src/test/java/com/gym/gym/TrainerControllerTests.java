package com.gym.gym;

import com.gym.gym.controllers.TrainerController;
import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.TrainerRegistrateRequest;
import com.gym.gym.dtos.request.TrainerUpdateRequest;
import com.gym.gym.dtos.response.TrainerFindResponse;
import com.gym.gym.dtos.response.TrainerUpdateResponse;
import com.gym.gym.entities.Trainer;
import com.gym.gym.mappers.TrainerMapper;
import com.gym.gym.services.implementations.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        ResponseEntity<TrainerFindResponse> responseEntity = trainerController.getTraineeByUsername(username);

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
        Credentials newCredentials = new Credentials("username", "password");
        when(trainerMapper.mapFromRegistrateRequest(request)).thenReturn(trainer);
        when(trainerService.createTrainer(trainer)).thenReturn(newTrainer);
        when(trainerMapper.mapToCredentials(newTrainer)).thenReturn(newCredentials);

        // Act
        ResponseEntity<Credentials> responseEntity = trainerController.createTrainer(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(newCredentials, responseEntity.getBody()); // Check response body

        // Verify that the mapper method and service method were called with the expected parameters
        verify(trainerMapper, times(1)).mapFromRegistrateRequest(request);
        verify(trainerService, times(1)).createTrainer(trainer);
        verify(trainerMapper, times(1)).mapToCredentials(newTrainer);
    }

    @Test
    public void updateTrainer() {
        // Arrange
        TrainerUpdateRequest request = new TrainerUpdateRequest();
        Trainer updatedTrainer = new Trainer();
        TrainerUpdateResponse response = new TrainerUpdateResponse();
        when(trainerMapper.mapFromUpdateRequest(request)).thenReturn(trainer);
        when(trainerService.updateTrainer(username, trainer, request.credentials)).thenReturn(updatedTrainer);
        when(trainerMapper.mapToUpdateResponse(updatedTrainer)).thenReturn(response);

        // Act
        ResponseEntity<TrainerUpdateResponse> responseEntity = trainerController.updateTrainer(username, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());

        verify(trainerMapper, times(1)).mapFromUpdateRequest(request);
        verify(trainerService, times(1)).updateTrainer(username, trainer, request.credentials);
        verify(trainerMapper, times(1)).mapToUpdateResponse(updatedTrainer);
    }
}
