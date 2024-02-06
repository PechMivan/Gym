package com.gym.gym;

import com.gym.gym.daos.implementations.TrainerDAOImpl;
import com.gym.gym.entities.Trainer;
import com.gym.gym.storages.DataStorageManager;
import org.junit.Test;
import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class TrainerDAOImplTests {

    @Mock
    DataStorageManager dataStorageManager;

    @InjectMocks
    TrainerDAOImpl trainerDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTrainerById() {
        long id = 1L;
        Trainer trainer = new Trainer();
        trainer.setUserId(id);

        Map<Long, Object> trainerMap = new HashMap<>();
        trainerMap.put(id, trainer);

        when(dataStorageManager.read("trainer")).thenReturn(trainerMap);

        Optional<Trainer> result = trainerDAO.get(id);

        assertEquals(trainer, result.orElse(null));
    }

    @Test
    public void getAllTrainers() {
        Trainer trainer1 = new Trainer();
        trainer1.setUserId(1L);

        Trainer trainer2 = new Trainer();
        trainer2.setUserId(2L);

        Map<Long, Object> trainerMap = new HashMap<>();
        trainerMap.put(1L, trainer1);
        trainerMap.put(2L, trainer2);

        when(dataStorageManager.read("trainer")).thenReturn(trainerMap);

        assertEquals(2, trainerDAO.getAll().size());
    }

    @Test
    public void saveTrainer() {
        Trainer trainer = new Trainer();
        trainer.setUserId(1L);

        Map<Long, Object> trainerMap = new HashMap<>();

        when(dataStorageManager.read("trainer")).thenReturn(trainerMap);

        trainerDAO.save(trainer);

        verify(dataStorageManager, times(1)).write("trainer", trainerMap);
        assertEquals(1, trainerMap.size());
    }

    @Test
    public void updateTrainer() {
        long id = 1L;
        Trainer existingTrainer = new Trainer();
        existingTrainer.setUserId(id);

        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setUserId(id);
        updatedTrainer.setFirstName("Updated Trainer");

        Map<Long, Object> trainerMap = new HashMap<>();
        trainerMap.put(id, existingTrainer);

        when(dataStorageManager.read("trainer")).thenReturn(trainerMap);

        trainerDAO.update(id, updatedTrainer);

        Trainer result = (Trainer)trainerMap.get(id);

        verify(dataStorageManager, times(1)).write("trainer", trainerMap);
        assertEquals("Updated Trainer", result.getFirstName());
    }

    @Test
    public void deleteTrainer() {
        long id = 1L;
        Trainer trainer = new Trainer();
        trainer.setUserId(id);

        Map<Long, Object> trainerMap = new HashMap<>();
        trainerMap.put(id, trainer);

        when(dataStorageManager.read("trainer")).thenReturn(trainerMap);

        trainerDAO.delete(id);

        verify(dataStorageManager, times(1)).write("trainer", trainerMap);
        assertEquals(0, trainerMap.size());
    }
}
