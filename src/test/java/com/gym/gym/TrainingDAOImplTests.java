package com.gym.gym;

import com.gym.gym.daos.implementations.TrainingDAOImpl;
import com.gym.gym.entities.Training;
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

public class TrainingDAOImplTests {

    @Mock
    DataStorageManager dataStorageManager;

    @InjectMocks
    TrainingDAOImpl trainingDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTrainerById() {
        long id = 1L;
        Training training = new Training();
        training.setTrainingId(id);

        Map<Long, Object> trainerMap = new HashMap<>();
        trainerMap.put(id, training);

        when(dataStorageManager.read("training")).thenReturn(trainerMap);

        Optional<Training> result = trainingDAO.findById(id);

        assertEquals(training, result.orElse(null));
    }

    @Test
    public void getAllTrainers() {
        Training training1 = new Training();
        training1.setTrainingId(1L);

        Training training2 = new Training();
        training2.setTrainingId(2L);

        Map<Long, Object> trainingMap = new HashMap<>();
        trainingMap.put(1L, training1);
        trainingMap.put(2L, training2);

        when(dataStorageManager.read("training")).thenReturn(trainingMap);

        assertEquals(2, trainingDAO.findAll().size());
    }

    @Test
    public void saveTrainer() {
        Training training = new Training();
        training.setTrainingId(1L);

        Map<Long, Object> trainingMap = new HashMap<>();

        when(dataStorageManager.read("training")).thenReturn(trainingMap);

        trainingDAO.save(training);

        verify(dataStorageManager, times(1)).write("training", trainingMap);
        assertEquals(1, trainingMap.size());
    }

    @Test
    public void deleteTrainer() {
        long id = 1L;
        Training training = new Training();
        training.setTrainingId(id);

        Map<Long, Object> trainingMap = new HashMap<>();
        trainingMap.put(id, training);

        when(dataStorageManager.read("training")).thenReturn(trainingMap);

        trainingDAO.delete(id);

        verify(dataStorageManager, times(1)).write("training", trainingMap);
        assertEquals(0, trainingMap.size());
    }
}
