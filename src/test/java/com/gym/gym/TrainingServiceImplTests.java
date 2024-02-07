package com.gym.gym;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gym.gym.daos.implementations.TrainingDAOImpl;
import com.gym.gym.entities.Training;
import com.gym.gym.services.implementations.TrainingServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class TrainingServiceImplTests {

    @Mock
    private TrainingDAOImpl trainingDAO;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTrainingById() {
        // Mock
        long id = 1L;
        Training training = new Training();
        training.setTrainingId(id);
        when(trainingDAO.findById(id)).thenReturn(Optional.of(training));

        // Test
        Training result = trainingService.getTrainingById(id);
        assertEquals(id, result.getTrainingId()); // Expecting the same training ID
    }

    @Test
    public void createTraining() {
        // Mock
        Training training = new Training();

        // Test
        trainingService.createTraining(training);
        verify(trainingDAO, times(1)).save(training); // Expecting save method to be called
    }
}
