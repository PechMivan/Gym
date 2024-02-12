package com.gym.gym;

import com.gym.gym.daos.implementations.TrainingDAOImpl;
import com.gym.gym.entities.Training;
import com.gym.gym.storages.DataStorageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

 class TrainingDAOImplTests {

    @Mock
    DataStorageManager dataStorageManager;

    @InjectMocks
    TrainingDAOImpl trainingDAO;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void getTrainerById() {
        // Arrange
        long id = 1L;
        Training training = new Training();
        training.setId(id);

        Map<Long, Object> trainerMap = new HashMap<>();
        trainerMap.put(id, training);

        when(dataStorageManager.read("training")).thenReturn(trainerMap);

        // Act
        Optional<Training> result = trainingDAO.findById(id);

        // Assert
        assertEquals(training, result.orElse(null));
    }

    @Test
     void getAllTrainers() {
        // Arrange
        Training training1 = new Training();
        training1.setId(1L);

        Training training2 = new Training();
        training2.setId(2L);

        Map<Long, Object> trainingMap = new HashMap<>();
        trainingMap.put(1L, training1);
        trainingMap.put(2L, training2);

        when(dataStorageManager.read("training")).thenReturn(trainingMap);

        // Act and Assert
        assertEquals(2, trainingDAO.findAll().size());
    }

    @Test
     void saveTrainer() {
        // Arrange
        Training training = new Training();
        training.setId(1L);

        Map<Long, Object> trainingMap = new HashMap<>();

        when(dataStorageManager.read("training")).thenReturn(trainingMap);

        // Act
        trainingDAO.save(training);

        // Assert
        verify(dataStorageManager, times(1)).write("training", trainingMap);
        assertEquals(1, trainingMap.size());
    }
}
