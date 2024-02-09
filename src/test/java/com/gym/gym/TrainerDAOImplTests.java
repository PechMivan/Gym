package com.gym.gym;

import com.gym.gym.daos.implementations.TrainerDAOImpl;
import com.gym.gym.entities.Trainer;
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

 class TrainerDAOImplTests {

    @Mock
    DataStorageManager dataStorageManager;

    @InjectMocks
    TrainerDAOImpl trainerDAO;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void getTrainerById() {
        // Arrange
        long id = 1L;
        Trainer trainer = new Trainer();
        trainer.setUserId(id);

        Map<Long, Object> trainerMap = new HashMap<>();
        trainerMap.put(id, trainer);

        when(dataStorageManager.read("trainer")).thenReturn(trainerMap);

        // Act
        Optional<Trainer> result = trainerDAO.findById(id);

        // Assert
        assertEquals(trainer, result.orElse(null));
    }

    @Test
     void getAllTrainers() {
        // Arrange
        Trainer trainer1 = new Trainer();
        trainer1.setUserId(1L);

        Trainer trainer2 = new Trainer();
        trainer2.setUserId(2L);

        Map<Long, Object> trainerMap = new HashMap<>();
        trainerMap.put(1L, trainer1);
        trainerMap.put(2L, trainer2);

        when(dataStorageManager.read("trainer")).thenReturn(trainerMap);

        // Act and Assert
        assertEquals(2, trainerDAO.findAll().size());
    }

    @Test
     void saveTrainer() {
        // Arrange
        Trainer trainer = new Trainer();
        trainer.setUserId(1L);

        Map<Long, Object> trainerMap = new HashMap<>();

        when(dataStorageManager.read("trainer")).thenReturn(trainerMap);

        // Act
        trainerDAO.save(trainer);

        // Assert
        verify(dataStorageManager, times(1)).write("trainer", trainerMap);
        assertEquals(1, trainerMap.size());
    }

    @Test
     void deleteTrainer() {
        // Arrange
        long id = 1L;
        Trainer trainer = new Trainer();
        trainer.setUserId(id);

        Map<Long, Object> trainerMap = new HashMap<>();
        trainerMap.put(id, trainer);

        when(dataStorageManager.read("trainer")).thenReturn(trainerMap);

        // Act
        trainerDAO.delete(id);

        // Assert
        verify(dataStorageManager, times(1)).write("trainer", trainerMap);
        assertEquals(0, trainerMap.size());
    }
}
