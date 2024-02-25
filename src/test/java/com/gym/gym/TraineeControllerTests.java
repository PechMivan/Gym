package com.gym.gym;

import com.gym.gym.controllers.TraineeController;
import com.gym.gym.dtos.response.TraineeFindResponse;
import com.gym.gym.entities.Trainee;
import com.gym.gym.mappers.TraineeMapper;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        username = "testUser";
        trainee = new Trainee();
    }

    @Test
    public void testGetTraineeByUsername() {
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
}
