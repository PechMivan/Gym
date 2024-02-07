package com.gym.gym;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import com.gym.gym.daos.implementations.TraineeDAOImpl;
import com.gym.gym.entities.Trainee;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TraineeServiceImplTests {

    @Mock
    private TraineeDAOImpl traineeDAO;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getTraineeById_Exists_ReturnsTrainee() {
        long id = 1L;
        Trainee trainee = new Trainee();
        trainee.setUserId(id);

        when(traineeDAO.findById(id)).thenReturn(Optional.of(trainee));

        Trainee result = traineeService.getTraineeById(id);

        assertEquals(trainee, result);
    }

    @Test
    public void getTraineeById_NotExists_ThrowsException() {
        long id = 1L;

        when(traineeDAO.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> traineeService.getTraineeById(id));
    }

    @Test
    public void getAllTrainees() {
        List<Trainee> trainees = new ArrayList<>();
        trainees.add(new Trainee());
        trainees.add(new Trainee());

        when(traineeDAO.findAll()).thenReturn(trainees);

        List<Trainee> result = traineeService.getAllTrainees();

        assertEquals(trainees, result);
    }

    @Test
    public void createTrainee() {
        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");

        when(traineeService.getAllTrainees()).thenReturn(new ArrayList<>());

        Trainee result = traineeService.createTrainee(trainee);

        assertNotNull(result.getUsername());
        assertNotNull(result.getPassword());
        verify(traineeDAO, times(1)).save(trainee);
    }

    @Test
    public void createUsername() {
        // Mocking existing trainees with similar username
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

        // Test
        String username = traineeService.createUsername("John", "Doe");
        assertEquals("John.Doe2", username); // Expecting "John.Doe2" as the username
    }

    @Test
    public void createPassword() {
        // Test
        String password = traineeService.createPassword();
        assertEquals(10, password.length()); // Expecting a password of length 10
    }

    @Test
    public void updateTrainee() {
        // Mock existing trainee
        long id = 1L;
        Trainee existingTrainee = new Trainee();
        existingTrainee.setUserId(id);

        // Mock updated trainee
        Trainee updatedTrainee = new Trainee();
        updatedTrainee.setFirstName("Updated");

        // Mock behavior of DAO
        when(traineeDAO.findById(id)).thenReturn(Optional.of(existingTrainee));

        // Test
        traineeService.updateTrainee(id, updatedTrainee);

        assertEquals("Updated", existingTrainee.getFirstName()); // Expecting the first name to be updated
        verify(traineeDAO, times(1)).save(existingTrainee); // Expecting save method to be called
    }

    @Test
    public void deleteTrainee() {
        // Mock
        long id = 1L;

        // Test
        traineeService.deleteTrainee(id);

        verify(traineeDAO, times(1)).delete(id); // Expecting delete method to be called with given id
    }


}
