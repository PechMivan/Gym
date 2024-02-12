package com.gym.gym;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.gym.gym.daos.implementations.TrainerDAOImpl;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.entities.User;
import com.gym.gym.services.implementations.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

 class TrainerServiceImplTests {

    @Mock
    private TrainerDAOImpl trainerDAO;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void getTrainerById_Exists_ReturnsTrainee() {
        // Arrange
        long id = 1L;
        Trainer trainer = new Trainer();
        trainer.setId(id);

        when(trainerDAO.findById(id)).thenReturn(Optional.of(trainer));

        // Act
        Trainer result = trainerService.getTrainerById(id);

        // Assert
        assertEquals(trainer, result);
    }

    @Test
     void getTrainerById_NotExists_ThrowsException() {
        // Arrange
        long id = 1L;

        when(trainerDAO.findById(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> trainerService.getTrainerById(id));
    }

    @Test
     void getAllTrainers() {
        // Arrange
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer());
        trainers.add(new Trainer());

        when(trainerDAO.findAll()).thenReturn(trainers);

        // Act
        List<Trainer> result = trainerService.getAllTrainers();

        // Assert
        assertEquals(trainers, result);
    }

    @Test
    void createTrainer() {
       // Arrange
       TrainerDTO trainerDTO = new TrainerDTO();
       trainerDTO.setFirstName("John");
       trainerDTO.setLastName("Doe");
       trainerDTO.setSpecialization("Yoga");
       trainerDTO.setActive(true);

       // Act
       Trainer result = trainerService.createTrainer(trainerDTO);

       // Assert
       assertNotNull(result);
       assertNotNull(result.getUser());
       assertEquals("John", result.getUser().getFirstName());
       assertEquals("Doe", result.getUser().getLastName());
       assertTrue(result.getUser().isActive());
       assertNotNull(result.getSpecialization());
       assertEquals("Yoga", result.getSpecialization().getTrainingTypeName());
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

        Trainer trainer1 = new Trainer();
        trainer1.setUser(user1);

        Trainer trainer2 = new Trainer();
        trainer2.setUser(user2);

        List<Trainer> existingTrainers = new ArrayList<>();
        existingTrainers.add(trainer1);
        existingTrainers.add(trainer2);

        when(trainerService.getAllTrainers()).thenReturn(existingTrainers);

        // Act
        String username = trainerService.createUsername("John", "Doe");

        // Assert
        assertEquals("John.Doe2", username); // Expecting "John.Doe2" as the username
    }

    @Test
    void createPassword() {
        // Act and Assert
        String password = trainerService.createPassword();
        assertEquals(10, password.length()); // Expecting a password of length 10
    }

    @Test
    void updateTrainer() {
       // Arrange

       long id = 1L;
       TrainingType trainingType = TrainingType.builder()
                                               .trainingTypeName("Yoga")
                                               .build();

       Trainer existingTrainer = new Trainer();
       existingTrainer.setId(id);
       existingTrainer.setUser(new User());
       existingTrainer.getUser().setUsername("oldusername");
       existingTrainer.getUser().setPassword("oldpassword");
       existingTrainer.setSpecialization(trainingType);

       TrainerDTO trainerDTO = new TrainerDTO();
       trainerDTO.setFirstName("John");
       trainerDTO.setLastName("Doe");
       trainerDTO.setSpecialization("Yoga");
       trainerDTO.setActive(true);

       when(trainerDAO.findById(id)).thenReturn(Optional.of(existingTrainer));

       // Act
       Trainer result = trainerService.updateTrainer(1L, trainerDTO);

       // Assert
       assertEquals("John", result.getUser().getFirstName());
       assertEquals("Doe", result.getUser().getLastName());
       assertEquals("oldusername", result.getUser().getUsername());
       assertEquals("oldpassword", result.getUser().getPassword());
       assertTrue(result.getUser().isActive());
       assertEquals("Yoga", result.getSpecialization().getTrainingTypeName());
    }
}
