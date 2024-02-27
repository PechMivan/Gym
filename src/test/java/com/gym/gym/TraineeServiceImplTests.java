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
import com.gym.gym.services.implementations.TrainerServiceImpl;
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

    @Mock
    TrainerServiceImpl trainerService;

    @InjectMocks
    TraineeServiceImpl traineeService;

    User user;
    Trainee trainee;
    String username;
    Credentials credentials;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        username = "John.Doe";
        String password = "passtest";

        this.credentials = new Credentials(username, password);

        this.user = User.builder()
                .id(1L)
                .firstname("John")
                .lastname("Doe")
                .username(username)
                .password(password)
                .isActive(true)
                .build();

        this.trainee = Trainee.builder()
                            .id(1L)
                            .user(user)
                            .dateOfBirth(new Date())
                            .address("20 Street").build();
    }

    @Test
    public void getAllTrainees_withTrainees_successful(){
        // Arrange
        List<Trainee> trainees = Arrays.asList(new Trainee(), new Trainee());
        when(traineeRepository.findAll()).thenReturn(trainees);
        // Act
        List<Trainee> result = traineeService.getAllTrainees();
        // Assert
        assertNotNull(result);
        assertEquals(trainees.size(), result.size());
    }

    @Test
    public void getAllTrainees_withoutTrainees_returnsEmptyList(){
        // Arrange
        List<Trainee> trainees = Collections.emptyList();
        when(traineeRepository.findAll()).thenReturn(trainees);
        // Act
        List<Trainee> result = traineeService.getAllTrainees();
        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void getTrainee_validId_successful(){
        // Arrange
        long traineeId = 1L;
        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));
        // Act
        Trainee result = traineeService.getTraineeById(traineeId);
        // Assert
        assertNotNull(result);
        assertEquals(traineeId, result.getId());
        assertEquals("John", result.getUser().getFirstname());
    }

    @Test
    public void getTrainee_invalidId_throwsNotFoundException(){
        // Act and Assert
        assertThrows(NotFoundException.class, () -> traineeService.getTraineeById(100L));
    }

    @Test
    public void getTrainee_validUsername_successful(){
        // Arrange
        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));
        // Act
        Trainee result = traineeService.getTraineeByUsername(username);
        // Assert
        assertNotNull(result);
        assertEquals(username, result.getUser().getUsername());
        assertEquals("John", result.getUser().getFirstname());
    }

    @Test
    public void getTrainee_invalidUsername_throwsNotFoundException(){
        // Act and Assert
        assertThrows(NotFoundException.class, () -> traineeService.getTraineeByUsername("wrongUsername"));
    }

    @Test
    public void createTrainee(){
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
    public void saveTrainee(){
        // Act
        traineeService.saveTrainee(trainee);
        // Assert
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    public void updateTrainee(){
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

        when(traineeRepository.findByUserUsername(anyString())).thenReturn(Optional.of(trainee));
        when(userService.updateUser(username, traineeUpdated.getUser())).thenReturn(userUpdated);
        // Act
        Trainee updatedTrainee = traineeService.updateTrainee(username, traineeUpdated, credentials);
        // Assert
        assertEquals("testName", updatedTrainee.getUser().getFirstname());
        assertEquals("testLastName", updatedTrainee.getUser().getLastname());
        assertEquals("testUsername", updatedTrainee.getUser().getUsername());
        assertEquals(8, updatedTrainee.getUser().getPassword().length());
        assertEquals("50 Street", updatedTrainee.getAddress());
        assertEquals(dateOfBirth, updatedTrainee.getDateOfBirth());
    }

    @Test
    public void deleteTrainee_validUsername_successful(){
        // Arrange
        when(traineeRepository.deleteByUserUsername(user.getUsername())).thenReturn(user.getId());
        when(traineeRepository.findByUserUsername(user.getUsername())).thenReturn(Optional.of(trainee));
        // Act
        long id = traineeService.deleteTraineeByUsername(user.getUsername(), new Credentials());
        // Assert
        assertEquals(user.getId(), id);
    }

    @Test
    public void deleteTrainee_invalidUsername_throwNotFoundException(){
        // Arrange
        when(traineeRepository.deleteByUserUsername(user.getUsername())).thenReturn(user.getId());
        when(traineeRepository.findByUserUsername(user.getUsername())).thenReturn(Optional.of(trainee));
        // Act and Assert
        assertThrows(NotFoundException.class, () -> traineeService.deleteTraineeByUsername("wrongUsername", credentials));
    }

    @Test
    public void updateTrainersList_oneTrainer_successful(){
        // Arrange
        Trainee trainee = Trainee.builder().trainers(new ArrayList<>()).build();
        when(traineeRepository.findById(anyLong())).thenReturn(Optional.of(trainee));
        // Act
        traineeService.updateTrainersList(1L, new Trainer());
        // Assert
        assertEquals(1, trainee.getTrainers().size());
        verify(traineeRepository, times(1)).save(trainee);
    }

    @Test
    public void updateTrainersList_multipleTrainers_successful(){
        // Arrange
        List<String> trainerUsernames = new ArrayList<>();
        trainerUsernames.add("trainer1");
        trainerUsernames.add("trainer2");
        Trainer trainer1 = Trainer.builder().id(1L).user(new User()).build();
        Trainer trainer2 = Trainer.builder().id(2L).user(new User()).build();

        when(traineeRepository.findByUserUsername(username)).thenReturn(Optional.of(trainee));

        when(trainerService.getTrainerByUsername(anyString())).thenReturn(trainer1);
        when(trainerService.getTrainerByUsername(anyString())).thenReturn(trainer2);

        when(traineeRepository.save(trainee)).thenReturn(trainee);

        // Act
        List<Trainer> updatedTrainers = traineeService.updateTrainerList(username, trainerUsernames, credentials);

        // Assert
        assertEquals(2, updatedTrainers.size());


        verify(userService, times(1)).authenticateUser(credentials.getUsername(), credentials.getPassword());
        verify(traineeRepository, times(1)).findByUserUsername(username);
        verify(trainerService, times(1)).getTrainerByUsername("trainer1");
        verify(trainerService, times(1)).getTrainerByUsername("trainer2");
        verify(traineeRepository, times(1)).save(trainee);
    }
}
