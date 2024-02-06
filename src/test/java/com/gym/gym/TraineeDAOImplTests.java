package com.gym.gym;

import com.gym.gym.daos.implementations.TraineeDAOImpl;
import com.gym.gym.entities.Trainee;
import com.gym.gym.storages.DataStorageManager;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TraineeDAOImplTests {

    @Mock
    DataStorageManager dataStorageManager;

    @InjectMocks
    TraineeDAOImpl traineeDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTraineeById() {
        long id = 1L;
        Trainee trainee = Trainee.builder()
                .firstName("Mario")
                .lastName("Pech")
                .address("Street 20")
                .username("Mapech")
                .password("pass")
                .dateOfBirth(new Date())
                .isActive(true)
                .userId(id)
                .build();

        Map<Long, Object> traineeMap = new HashMap<>();
        traineeMap.put(id, trainee);

        when(dataStorageManager.read("trainee")).thenReturn(traineeMap);

        Optional<Trainee> result = traineeDAO.get(id);

        assertEquals(trainee, result.orElse(null));
    }

    @Test
    public void getAllTrainees() {
        Trainee trainee1 = Trainee.builder()
                .firstName("Mario")
                .lastName("Pech")
                .address("Street 20")
                .username("Mapech")
                .password("pass")
                .dateOfBirth(new Date())
                .isActive(true)
                .userId(1L)
                .build();
        Trainee trainee2 = Trainee.builder()
                .firstName("firstname")
                .lastName("lastname")
                .address("address")
                .username("username")
                .password("pass")
                .dateOfBirth(new Date())
                .isActive(false)
                .userId(2L)
                .build();

        Map<Long, Object> traineeMap = new HashMap<>();
        traineeMap.put(1L, trainee1);
        traineeMap.put(2L, trainee2);

        when(dataStorageManager.read("trainee")).thenReturn(traineeMap);

        assertEquals(2, traineeDAO.getAll().size());
    }

    @Test
    public void saveTrainee() {
        Trainee trainee = Trainee.builder()
                .firstName("Mario")
                .lastName("Pech")
                .address("Street 20")
                .username("Mapech")
                .password("pass")
                .dateOfBirth(new Date())
                .isActive(true)
                .userId(1L)
                .build();

        Map<Long, Object> traineeMap = new HashMap<>();

        when(dataStorageManager.read("trainee")).thenReturn(traineeMap);

        traineeDAO.save(trainee);

        verify(dataStorageManager, times(1)).write("trainee", traineeMap);
        assertEquals(1, traineeMap.size());
    }

    @Test
    public void updateTrainee() {
        long id = 1L;
        Trainee existingTrainee = Trainee.builder()
                .firstName("Mario")
                .lastName("Pech")
                .address("Street 20")
                .username("Mapech")
                .password("pass")
                .dateOfBirth(new Date())
                .isActive(true)
                .userId(id)
                .build();

        Trainee updatedTrainee = Trainee.builder()
                .firstName("Mario")
                .lastName("Pech")
                .address("Street 20")
                .username("Mapech")
                .password("pass")
                .dateOfBirth(new Date())
                .isActive(true)
                .userId(id)
                .build();

        updatedTrainee.setFirstName("Updated");

        Map<Long, Object> traineeMap = new HashMap<>();
        traineeMap.put(id, existingTrainee);

        when(dataStorageManager.read("trainee")).thenReturn(traineeMap);

        traineeDAO.update(id, updatedTrainee);

        Trainee result = (Trainee)traineeMap.get(id);

        verify(dataStorageManager, times(1)).write("trainee", traineeMap);
        assertEquals("Updated", result.getFirstName());
    }

    @Test
    public void deleteTrainee() {
        long id = 1L;
        Trainee trainee = Trainee.builder()
                .firstName("Mario")
                .lastName("Pech")
                .address("Street 20")
                .username("Mapech")
                .password("pass")
                .dateOfBirth(new Date())
                .isActive(true)
                .userId(1L)
                .build();
        trainee.setUserId(id);

        Map<Long, Object> traineeMap = new HashMap<>();
        traineeMap.put(id, trainee);

        when(dataStorageManager.read("trainee")).thenReturn(traineeMap);

        traineeDAO.delete(id);

        verify(dataStorageManager, times(1)).write("trainee", traineeMap);
        assertEquals(0, traineeMap.size());
    }

}

