package com.gym.gym;

import com.gym.gym.daos.implementations.TraineeDAOImpl;
import com.gym.gym.entities.Trainee;
import com.gym.gym.storages.DataStorageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

 class TraineeDAOImplTests {

    @Mock
    DataStorageManager dataStorageManager;

    @InjectMocks
    TraineeDAOImpl traineeDAO;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void getTraineeById() {
        // Arrange
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

        // Act
        Optional<Trainee> result = traineeDAO.findById(id);

        // Assert
        assertEquals(trainee, result.orElse(null));
    }

    @Test
     void getAllTrainees() {
        // Arrange
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

        // Act and Assert
        assertEquals(2, traineeDAO.findAll().size());
    }

    @Test
     void saveTrainee() {
        // Arrange
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

        // Act
        traineeDAO.save(trainee);

        // Assert
        verify(dataStorageManager, times(1)).write("trainee", traineeMap);
        assertEquals(1, traineeMap.size());
    }

    @Test
     void deleteTrainee() {
        // Arrange
        long id = 1L;
        Trainee trainee = Trainee.builder()
                .firstName("Mario")
                .lastName("Pech")
                .address("Street 20")
                .username("Mapech")
                .password("pass")
                .dateOfBirth(new Date())
                .isActive(true)
                .build();
        trainee.setUserId(id);

        Map<Long, Object> traineeMap = new HashMap<>();
        traineeMap.put(id, trainee);

        when(dataStorageManager.read("trainee")).thenReturn(traineeMap);

        // Act
        traineeDAO.delete(id);

        // Assert
        verify(dataStorageManager, times(1)).write("trainee", traineeMap);
        assertEquals(0, traineeMap.size());
    }

}

