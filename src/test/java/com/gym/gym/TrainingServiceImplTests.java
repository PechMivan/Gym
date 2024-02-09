package com.gym.gym;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.gym.gym.daos.implementations.TrainingDAOImpl;
import com.gym.gym.entities.Training;
import com.gym.gym.services.implementations.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

 class TrainingServiceImplTests {

    @Mock
    private TrainingDAOImpl trainingDAO;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void getTrainingById() {
        // Arrange
        long id = 1L;
        Training training = new Training();
        training.setTrainingId(id);
        when(trainingDAO.findById(id)).thenReturn(Optional.of(training));

        // Act
        Training result = trainingService.getTrainingById(id);

        // Assert
        assertEquals(id, result.getTrainingId()); // Expecting the same training ID
    }

    @Test
     void createTraining() {
        // Arrange
        Training training = new Training();

        // Act
        trainingService.createTraining(training);

        // Assert
        verify(trainingDAO, times(1)).save(training); // Expecting save method to be called
    }
}
