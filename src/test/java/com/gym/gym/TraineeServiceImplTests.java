package com.gym.gym;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TraineeRepository;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import com.gym.gym.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

public class TraineeServiceImplTests {

    @Mock
    TraineeRepository traineeRepository;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    TraineeServiceImpl traineeService;

    User user;
    Trainee trainee;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        this.user = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .username("John.Doe")
                .password("passtest")
                .isActive(true)
                .build();

        this.trainee = Trainee.builder()
                            .id(1L)
                            .user(user)
                            .dateOfBirth(new Date())
                            .address("20 Street").build();
    }

    @Test
    public void testGetAllTrainees(){
        // Arrange
        List<Trainee> trainees = Arrays.asList(new Trainee(), new Trainee());
        when(traineeRepository.findAll()).thenReturn(trainees);
        // Act
        List<Trainee> traineeList = traineeService.getAllTrainees();
        // Assert
        assertNotNull(traineeList);
        assertEquals(trainees.size(), traineeList.size());
    }

    @Test
    public void testGetTraineeById(){
        // Arrange
        long traineeId = 1L;
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));
        // Act
        Trainee result = traineeService.getTraineeById(traineeId);
        // Assert
        assertNotNull(result);
        assertEquals(traineeId, result.getId());
        assertEquals("John", result.getUser().getFirstname());
        assertThrows(NotFoundException.class, () -> traineeService.getTraineeById(100L));
    }

    @Test
    public void testGetTraineeByUsername(){
        // Arrange
        String username = "John.Doe";
        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));
        // Act
        Trainee result = traineeService.getTraineeByUsername(username);
        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUser().getUsername());
        assertEquals("John", result.getUser().getFirstname());
        assertThrows(NotFoundException.class, () -> traineeService.getTraineeByUsername("wrongUsername"));
    }

    @Test
    public void testCreateTrainee(){
        // Arrange
        Date dateOfBirth = trainee.getDateOfBirth();
        when(userService.createUser(trainee.getUser())).thenReturn(user);
        // Act
        Trainee createdTrainee = traineeService.createTrainee(trainee);
        // Assert
        assertEquals("John", createdTrainee.getUser().getFirstname());
        assertEquals("Doe", createdTrainee.getUser().getLastname());
        assertEquals("John.Doe", createdTrainee.getUser().getUsername());
        assertEquals(8, createdTrainee.getUser().getPassword().length());
        assertEquals("20 Street", createdTrainee.getAddress());
        assertEquals(dateOfBirth, createdTrainee.getDateOfBirth());
    }

    @Test
    public void testSaveTrainee(){
        // Act
        traineeService.saveTrainee(trainee);
        // Assert
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    public void testUpdateTrainee(){
        // Arrange
        User userUpdated = User.builder()               // User service should return update user
                            .firstname("testName")
                            .lastname("testLastName")
                            .username("testUsername")
                            .password("passtest")
                            .build();

        Trainee traineeUpdated = Trainee.builder()
                                      .user(userUpdated)
                                      .address("50 Street")
                                      .dateOfBirth(new Date())
                                      .build();

        Date dateOfBirth = traineeUpdated.getDateOfBirth();

        when(traineeRepository.findById(anyLong())).thenReturn(Optional.of(trainee));
        when(userService.updateUser(traineeUpdated.getUser())).thenReturn(userUpdated);
        // Act
        Trainee updatedTrainee = traineeService.updateTrainee(1L, traineeUpdated);
        // Assert
        assertEquals("testName", updatedTrainee.getUser().getFirstname());
        assertEquals("testLastName", updatedTrainee.getUser().getLastname());
        assertEquals("testUsername", updatedTrainee.getUser().getUsername());
        assertEquals(8, updatedTrainee.getUser().getPassword().length());
        assertEquals("50 Street", updatedTrainee.getAddress());
        assertEquals(dateOfBirth, updatedTrainee.getDateOfBirth());
    }


    @Test
    public void testDeleteTraineeById(){
        // Act
        traineeService.deleteTrainee(1L, new Credentials());
        // Assert
        verify(traineeRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteTraineeByUsername(){
        // Act
        traineeService.deleteTraineeByUsername("test", new Credentials());
        // Assert
        verify(traineeRepository, times(1)).deleteByUserUsername("test");
    }

    @Test
    public void testToggleTraineeActive(){
        // Act
        traineeService.toggleTraineeActive(1L, new Credentials());
        // Assert
        verify(userService, times(1)).toggleActive(1L);
    }

    @Test
    public void testChangePassword(){
        // Act
        traineeService.changePassword("username", "oldPassword", "newPassword");
        // Assert
        verify(userService, times(1))
                .changePassword("username", "oldPassword", "newPassword");
    }

    @Test
    public void testUpdateTrainersList(){
        // Arrange
        Trainee trainee = Trainee.builder().trainers(new ArrayList<>()).build();
        when(traineeRepository.findById(anyLong())).thenReturn(Optional.of(trainee));
        // Act
        traineeService.updateTrainersList(1L, new Trainer());
        // Assert
        assertEquals(1, trainee.getTrainers().size());
        verify(traineeRepository, times(1)).save(trainee);
    }
}
