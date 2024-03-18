package com.gym.gym;

import com.gym.gym.controllers.TraineeController;
import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.CredentialsAndAccessToken;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.dtos.request.TraineeRegistrateRequest;
import com.gym.gym.dtos.request.TraineeTrainersListUpdateRequest;
import com.gym.gym.dtos.request.TraineeUpdateRequest;
import com.gym.gym.dtos.response.TraineeFindResponse;
import com.gym.gym.dtos.response.TraineeUpdateResponse;
import com.gym.gym.entities.Token;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.User;
import com.gym.gym.mappers.TraineeMapper;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.Mockito.*;

public class TraineeControllerTests {

    @Mock
    TraineeServiceImpl traineeService;

    @Mock
    TraineeMapper traineeMapper;

    @InjectMocks
    TraineeController traineeController;

    String username;
    Trainee trainee;
    Credentials credentials;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        username = "testUser";
        trainee = new Trainee();
        credentials = new Credentials();
    }

    @Test
    public void getTraineeByUsername() {
        // Arrange
        TraineeFindResponse findResponse = new TraineeFindResponse(); // Create a response object
        when(traineeService.getTraineeByUsername(username)).thenReturn(trainee);
        when(traineeMapper.mapToFindResponse(trainee)).thenReturn(findResponse);

        // Act
        ResponseEntity<TraineeFindResponse> responseEntity = traineeController.getTraineeByUsername(username);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code
        assertEquals(findResponse, responseEntity.getBody()); // Check response body

        // Verify that the service method and mapper method were called
        verify(traineeService, times(1)).getTraineeByUsername(username);
        verify(traineeMapper, times(1)).mapToFindResponse(trainee);
    }

    @Test
    public void createTrainee() {
        // Arrange
        TraineeRegistrateRequest request = new TraineeRegistrateRequest();
        User user = new User();
        Trainee newTrainee = new Trainee();
        Token accessToken = new Token();

        accessToken.setToken("tokenString");
        user.setUsername(username);
        user.setPassword("password");
        user.setTokens(Collections.singletonList(accessToken));
        newTrainee.setUser(user);

        when(traineeMapper.mapFromRegistrateRequest(request)).thenReturn(trainee);
        when(traineeService.createTrainee(trainee)).thenReturn(newTrainee);

        // Act
        ResponseEntity<CredentialsAndAccessToken> responseEntity = traineeController.createTrainee(request);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertInstanceOf(CredentialsAndAccessToken.class, responseEntity.getBody());
        assertEquals(accessToken.getToken(), responseEntity.getBody().getAccessToken());
        assertEquals(user.getUsername(), responseEntity.getBody().getCredentials().getUsername());
        assertEquals(user.getPassword(), responseEntity.getBody().getCredentials().getPassword());

        verify(traineeMapper, times(1)).mapFromRegistrateRequest(request);
        verify(traineeService, times(1)).createTrainee(trainee);
    }

    @Test
    public void updateTrainee() {
        // Arrange
        TraineeUpdateRequest request = new TraineeUpdateRequest();
        Trainee updatedTrainee = new Trainee();
        TraineeUpdateResponse response = new TraineeUpdateResponse();
        when(traineeMapper.mapFromUpdateRequest(request)).thenReturn(trainee);
        when(traineeService.updateTrainee(username, trainee)).thenReturn(updatedTrainee);
        when(traineeMapper.mapToUpdateResponse(updatedTrainee)).thenReturn(response);

        // Act
        ResponseEntity<TraineeUpdateResponse> responseEntity = traineeController.updateTrainee(username, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());

        verify(traineeMapper, times(1)).mapFromUpdateRequest(request);
        verify(traineeService, times(1)).updateTrainee(username, trainee);
        verify(traineeMapper, times(1)).mapToUpdateResponse(updatedTrainee);
    }

    @Test
    public void deleteTraineeByUsername() {
        // Act
        ResponseEntity<String> responseEntity = traineeController.deleteTraineeByUsername(username);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode()); // Check status code

        // Verify that the service method was called with the expected parameters
        verify(traineeService, times(1)).deleteTraineeByUsername(username);
    }

    @Test
    public void updateTrainerList() {
        // Arrange
        TraineeTrainersListUpdateRequest request = new TraineeTrainersListUpdateRequest();
        List<Trainer> updatedTrainerList = new ArrayList<>();
        List<TrainerDTO> response = new ArrayList<>();
        when(traineeService.updateTrainerList(username, request.getUsernames())).thenReturn(updatedTrainerList);
        when(traineeMapper.trainerListToTrainerDTOList(updatedTrainerList)).thenReturn(response);

        // Act
        ResponseEntity<List<TrainerDTO>> responseEntity = traineeController.updateTrainerList(username, request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());

        verify(traineeService, times(1)).updateTrainerList(username, request.getUsernames());
        verify(traineeMapper, times(1)).trainerListToTrainerDTOList(updatedTrainerList);
    }
}
