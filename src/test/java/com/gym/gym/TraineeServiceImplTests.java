package com.gym.gym;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gym.gym.daos.implementations.TraineeDAOImpl;
import com.gym.gym.entities.Trainee;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

 class TraineeServiceImplTests {

    @Mock
    private TraineeDAOImpl traineeDAO;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void getTraineeById_Exists_ReturnsTrainee() {
        // Arrange
        long id = 1L;
        Trainee trainee = new Trainee();
        trainee.setUserId(id);

        when(traineeDAO.findById(id)).thenReturn(Optional.of(trainee));

        // Act
        Trainee result = traineeService.getTraineeById(id);

        // Assert
        assertEquals(trainee, result);
    }

    @Test
     void getTraineeById_NotExists_ThrowsException() {
        // Arrange
        long id = 1L;

        when(traineeDAO.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> traineeService.getTraineeById(id));
    }

    @Test
     void getAllTrainees() {
        // Arrange
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(new Trainee());
        trainees.add(new Trainee());

        when(traineeDAO.findAll()).thenReturn(trainees);

        // Act
        List<Trainee> result = traineeService.getAllTrainees();

        // Assert
        assertEquals(trainees, result);
    }

    @Test
     void createTrainee() {
        // Arrange
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");

        when(traineeService.getAllTrainees()).thenReturn(new ArrayList<>());

        // Act
        Trainee result = traineeService.createTrainee(trainee);

        // Assert
        assertNotNull(result.getUsername());
        assertNotNull(result.getPassword());
        verify(traineeDAO, times(1)).save(trainee);
    }

    @Test
     void createUsername() {
        // Arrange
        Trainee trainee1 = new Trainee();
        trainee1.setFirstName("John");
        trainee1.setLastName("Doe");
        trainee1.setUsername("John.Doe");

        Trainee trainee2 = new Trainee();
        trainee2.setFirstName("John");
        trainee2.setLastName("Doe");
        trainee2.setUsername("John.Doe1");

        List<Trainee> existingTrainees = new ArrayList<>();
        existingTrainees.add(trainee1);
        existingTrainees.add(trainee2);

        when(traineeService.getAllTrainees()).thenReturn(existingTrainees);

        // Act
        String username = traineeService.createUsername("John", "Doe");

        // Assert
        assertEquals("John.Doe2", username); // Expecting "John.Doe2" as the username
    }

    @Test
     void createPassword() {
        // Act
        String password = traineeService.createPassword();
        // Assert
        assertEquals(10, password.length()); // Expecting a password of length 10
    }

    @Test
     void updateTrainee() {
        // Arrange
        long id = 1L;
        Trainee existingTrainee = new Trainee();
        existingTrainee.setUserId(id);

        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setFirstName("Updated");

        when(traineeDAO.findById(id)).thenReturn(Optional.of(existingTrainee));

        // Act
        traineeService.updateTrainee(id, updatedTrainee);

        // Assert
        assertEquals("Updated", existingTrainee.getFirstName()); // Expecting the first name to be updated
        verify(traineeDAO, times(1)).save(existingTrainee); // Expecting save method to be called
    }

    @Test
     void deleteTrainee() {
        // Arrange
        long id = 1L;

        // Act
        traineeService.deleteTrainee(id);

        // Assert
        verify(traineeDAO, times(1)).delete(id); // Expecting delete method to be called with given id
    }


}
