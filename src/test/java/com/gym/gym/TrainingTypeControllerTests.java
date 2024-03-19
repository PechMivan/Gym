package com.gym.gym;

import com.gym.gym.controllers.TrainingTypeController;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.services.TrainingTypeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrainingTypeControllerTests {

    @Mock
    TrainingTypeService trainingTypeService;

    @InjectMocks
    TrainingTypeController trainingTypeController;

    @Test
    void getAllTrainingTypes() {
        // Arrange
        List<TrainingType> trainingTypes = new ArrayList<>();
        trainingTypes.add(new TrainingType());
        trainingTypes.add(new TrainingType());
        when(trainingTypeService.getAllTrainingTypes()).thenReturn(trainingTypes);

        // Act
        ResponseEntity<List<TrainingType>> responseEntity = trainingTypeController.getAllTrainingTypes();

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(trainingTypes, responseEntity.getBody());
        assertEquals(2, trainingTypes.size());
        verify(trainingTypeService, times(1)).getAllTrainingTypes();
    }
}
