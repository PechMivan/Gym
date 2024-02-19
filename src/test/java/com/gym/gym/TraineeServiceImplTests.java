package com.gym.gym;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.dtos.UserDTO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TraineeRepository;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import com.gym.gym.services.implementations.UserServiceImpl;
import jakarta.validation.Validator;
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
    Validator validator;

    @InjectMocks
    TraineeServiceImpl traineeService;

    User user;
    UserDTO userDTO;
    Trainee trainee;
    TraineeDTO traineeDTO;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        this.user = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .username("John.Doe")
                .password("passtest")
                .isActive(true)
                .build();

        this.trainee = Trainee.builder()
                            .id(1L)
                            .user(user)
                            .dateOfBirth(new Date())
                            .address("20 Street").build();

        this.userDTO = UserDTO.builder()
                              .firstname("John")
                              .lastname("Doe")
                              .username("John.Doe")
                              .password("passtest")
                              .build();

        this.traineeDTO = TraineeDTO.builder()
                                    .dateOfBirth("2020-12-18")
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
        assertEquals("John", result.getUser().getFirstName());
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
        assertEquals("John", result.getUser().getFirstName());
        assertThrows(NotFoundException.class, () -> traineeService.getTraineeByUsername("wrongUsername"));
    }

    @Test
    public void testCreateTrainee(){
        // Arrange
        Date dateOfBirth = traineeService.createDate(traineeDTO.dateOfBirth);
        when(userService.createUser(traineeDTO.userDTO)).thenReturn(user);
        // Act
        Trainee createdTrainee = traineeService.createTrainee(traineeDTO);
        // Assert
        assertEquals("John", createdTrainee.getUser().getFirstName());
        assertEquals("Doe", createdTrainee.getUser().getLastName());
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
                            .firstName("testName")
                            .lastName("testLastName")
                            .username("John.Doe")
                            .password("passtest")
                            .build();

        UserDTO userDTOUpdated = UserDTO.builder()      // Needed for the corresponding user update
                .firstname("testName")
                .lastname("testLastName")
                .username("John.Doe")
                .password("passtest")
                .build();

        TraineeDTO traineeDTOUpdated = TraineeDTO.builder() // Actual trainee update
                                .userDTO(userDTOUpdated)
                                .address("50 Street")
                                .dateOfBirth("2010-10-10")
                                .build();

        Date dateOfBirth = traineeService.createDate(traineeDTOUpdated.dateOfBirth);

        when(traineeRepository.findById(anyLong())).thenReturn(Optional.of(trainee));
        when(userService.updateUser(traineeDTOUpdated.userDTO)).thenReturn(userUpdated);
        // Act
        Trainee updatedTrainee = traineeService.updateTrainee(1L, traineeDTOUpdated);
        // Assert
        assertEquals("testName", updatedTrainee.getUser().getFirstName());
        assertEquals("testLastName", updatedTrainee.getUser().getLastName());
        assertEquals("John.Doe", updatedTrainee.getUser().getUsername());
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
