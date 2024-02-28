package com.gym.gym;

import com.gym.gym.controllers.TrainingController;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.dtos.request.TraineeTrainingFindRequest;
import com.gym.gym.dtos.request.TrainerTrainingFindRequest;
import com.gym.gym.dtos.request.TrainingCreateRequest;
import com.gym.gym.dtos.response.TraineeTrainingFindResponse;
import com.gym.gym.dtos.response.TrainerTrainingFindResponse;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import com.gym.gym.mappers.TrainingMapper;
import com.gym.gym.services.implementations.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TrainingControllerTests {

    @Mock
    TrainingServiceImpl trainingService;

    @Mock
    TrainingMapper trainingMapper;

    @InjectMocks
    TrainingController trainingController;

    Training training;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        this.training = new Training();
    }

    @Test
    public void createTraining() {
        // Arrange
        TrainingCreateRequest request = new TrainingCreateRequest();
        Training newTraining = new Training();
        when(trainingMapper.mapFromCreateRequest(request)).thenReturn(training);
        when(trainingService.createTraining(training)).thenReturn(newTraining);

        // Act
        ResponseEntity<HttpStatus> responseEntity = trainingController.createTraining(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(trainingMapper, times(1)).mapFromCreateRequest(request);
        verify(trainingService, times(1)).createTraining(training);
    }

    @Test
    public void getAllTrainersNotInTraineeTrainersListByUsername() {
        // Arrange
        String username = "testUser";
        List<Trainer> trainers = new ArrayList<>();
        List<TrainerDTO> response = new ArrayList<>();
        when(trainingService.getAllTrainersNotInTraineeTrainersListByUsername(username)).thenReturn(trainers);
        when(trainingMapper.mapTrainerListToTrainerDTOList(trainers)).thenReturn(response);

        // Act
        ResponseEntity<List<TrainerDTO>> responseEntity = trainingController.getAllTrainersNotInTraineeTrainersListByUsername(username);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(response, responseEntity.getBody());
        verify(trainingService, times(1)).getAllTrainersNotInTraineeTrainersListByUsername(username);
        verify(trainingMapper, times(1)).mapTrainerListToTrainerDTOList(trainers);
    }

    @Test
    public void testGetFilteredTrainingsForTrainee() {
        // Arrange
        TraineeTrainingFindRequest request = new TraineeTrainingFindRequest();
        List<Training> trainings = new ArrayList<>();
        List<TraineeTrainingFindResponse> responses = new ArrayList<>();
        when(trainingService.getFilteredTrainingsForTrainee(request)).thenReturn(trainings);
        when(trainingMapper.mapToFindTraineeTrainingResponseList(trainings)).thenReturn(responses);

        // Act
        ResponseEntity<List<TraineeTrainingFindResponse>> responseEntity = trainingController.getFilteredTrainingsForTrainee(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responses, responseEntity.getBody());
        verify(trainingService, times(1)).getFilteredTrainingsForTrainee(request);
        verify(trainingMapper, times(1)).mapToFindTraineeTrainingResponseList(trainings);
    }

    @Test
    public void testGetFilteredTrainingsForTrainer() {
        // Arrange
        TrainerTrainingFindRequest request = new TrainerTrainingFindRequest();
        List<Training> trainings = new ArrayList<>();
        List<TrainerTrainingFindResponse> responses = new ArrayList<>();
        when(trainingService.getFilteredTrainingsForTrainer(request)).thenReturn(trainings);
        when(trainingMapper.mapToFindTrainerTrainingResponseList(trainings)).thenReturn(responses);

        // Act
        ResponseEntity<List<TrainerTrainingFindResponse>> responseEntity = trainingController.getFilteredTrainingsForTrainer(request);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(responses, responseEntity.getBody());
        verify(trainingService, times(1)).getFilteredTrainingsForTrainer(request);
        verify(trainingMapper, times(1)).mapToFindTrainerTrainingResponseList(trainings);
    }
}

