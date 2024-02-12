package com.gym.gym;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gym.gym.daos.implementations.TraineeDAOImpl;
import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.User;
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
        trainee.setId(id);

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
       TraineeDTO traineeDTO = new TraineeDTO();
       traineeDTO.setFirstName("John");
       traineeDTO.setLastName("Doe");
       traineeDTO.setDateOfBirth("01-01-1990");
       traineeDTO.setAddress("123 Street");
       traineeDTO.setActive(true);

       // Act
       Trainee createdTrainee = traineeService.createTrainee(traineeDTO);

       // Assert
       assertEquals("John", createdTrainee.getUser().getFirstName());
       assertEquals("Doe", createdTrainee.getUser().getLastName());
       assertEquals("John.Doe", createdTrainee.getUser().getUsername());
       assertEquals("123 Street", createdTrainee.getAddress());
       assertTrue(createdTrainee.getUser().isActive());
       verify(traineeDAO, times(1)).save(any(Trainee.class));
    }

    @Test
     void createUsername() {
        // Arrange
        User user1 = User.builder()
                         .firstName("John")
                         .lastName("Doe")
                         .username("John.Doe")
                         .build();

       User user2 = User.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .username("John.Doe1")
                        .build();

        Trainee trainee1 = new Trainee();
        trainee1.setUser(user1);

        Trainee trainee2 = new Trainee();
        trainee2.setUser(user2);

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
    void UpdateTrainee(){
       // Arrange
       long id = 1L;
       TraineeDTO traineeDTO = new TraineeDTO();
       traineeDTO.setFirstName("John");
       traineeDTO.setLastName("Doe");
       traineeDTO.setDateOfBirth("01-01-1990");
       traineeDTO.setAddress("123 Street");
       traineeDTO.setActive(true);

       Trainee existingTrainee = new Trainee();
       existingTrainee.setId(id);
       existingTrainee.setUser(new User());

       when(traineeDAO.findById(id)).thenReturn(Optional.of(existingTrainee));

       // Act
       Trainee result = traineeService.updateTrainee(id, traineeDTO);

       // Assert
       verify(traineeDAO, times(1)).findById(id);
       verify(traineeDAO, times(1)).save(any(Trainee.class));
       assertEquals(traineeDTO.getFirstName(), result.getUser().getFirstName());
       assertEquals(traineeDTO.getLastName(), result.getUser().getLastName());
       assertEquals(traineeDTO.getAddress(), result.getAddress());
       assertTrue(result.getUser().isActive());
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
