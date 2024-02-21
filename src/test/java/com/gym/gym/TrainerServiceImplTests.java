package com.gym.gym;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainerRepository;
import com.gym.gym.services.implementations.TrainerServiceImpl;
import com.gym.gym.services.implementations.TrainingTypeServiceImpl;
import com.gym.gym.services.implementations.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class TrainerServiceImplTests {

    @Mock
    UserServiceImpl userService;

    @Mock
    TrainingTypeServiceImpl trainingTypeService;

    @Mock
    TrainerRepository trainerRepository;

    @InjectMocks
    TrainerServiceImpl trainerService;

    User user;
    TrainingType trainingType;
    Trainer trainer;

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

        this.trainingType = TrainingType.builder()
                                        .id(1L)
                                        .trainingTypeName("HIIT")
                                        .build();

        this.trainer = Trainer.builder()
                              .id(1L)
                              .user(user)
                              .specialization(trainingType)
                              .build();
    }

    @Test
    public void testGetAllTrainers(){
        // Arrange
        List<Trainer> trainers = Arrays.asList(new Trainer(), new Trainer());
        when(trainerRepository.findAll()).thenReturn(trainers);
        // Act
        List<Trainer> trainersList = trainerService.getAllTrainers();
        // Assert
        assertEquals(trainers.size(), trainersList.size());
    }

    @Test
    public void testGetTrainerById(){
        // Arrange
        long trainerId = 1L;
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainer));
        // Act
        Trainer result = trainerService.getTrainerById(1L);
        // Assert
        assertNotNull(result);
        assertEquals(trainerId, result.getId());
        assertEquals("John", result.getUser().getFirstName());
        assertThrows(NotFoundException.class, () ->trainerService.getTrainerById(100L));
    }

    @Test
    public void testGetTrainerByUsername(){
        // Arrange
        String username = "John.Doe";
        when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.of(trainer));
        // Act
        Trainer result = trainerService.getTrainerByUsername(username);
        // Assert
        assertNotNull(trainer);
        assertEquals(username, result.getUser().getUsername());
        assertEquals("John", result.getUser().getFirstName());
        assertThrows(NotFoundException.class, () ->trainerService.getTrainerByUsername("wrongUsername"));
    }

    @Test
    public void testCreateTrainer(){
        // Arrange
        when(userService.createUser(trainer.getUser())).thenReturn(user);
        when(trainingTypeService
                .getTrainingTypeByName(trainer.getSpecialization().getTrainingTypeName()))
                .thenReturn(trainingType);
        // Act
        Trainer createdTrainer = trainerService.createTrainer(trainer);
        // Assert
        assertEquals("John", createdTrainer.getUser().getFirstName());
        assertEquals("Doe", createdTrainer.getUser().getLastName());
        assertEquals("John.Doe", createdTrainer.getUser().getUsername());
        assertEquals(8, createdTrainer.getUser().getPassword().length());
        assertEquals("HIIT", createdTrainer.getSpecialization().getTrainingTypeName());
    }

    @Test
    public void testSaveTrainer(){
        // Act
        trainerService.saveTrainer(trainer);
        // Assert
        verify(trainerRepository, times(1)).save(trainer);
    }

    @Test
    public void testUpdateTrainer() {
        // Arrange
        User userUpdated = User.builder()
                .id(1L)
                .firstName("testName")
                .build();

        TrainingType trainingTypeUpdated = TrainingType.builder()
                .id(2L)
                .trainingTypeName("CARDIO")
                .build();
        
        Trainer updateTrainer = Trainer.builder()
                                       .user(userUpdated)
                                       .specialization(trainingTypeUpdated)
                                       .build();

        when(userService.updateUser(updateTrainer.getUser())).thenReturn(userUpdated);
        when(trainingTypeService
                .getTrainingTypeByName(updateTrainer.getSpecialization().getTrainingTypeName()))
                .thenReturn(trainingTypeUpdated);
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        // Act
        Trainer updatedTrainer = trainerService.updateTrainer(1L, updateTrainer);
        // Assert
        assertEquals("testName", updatedTrainer.getUser().getFirstName());
        assertEquals(updateTrainer.getSpecialization().getTrainingTypeName(), updatedTrainer.getSpecialization().getTrainingTypeName());
    }


    @Test
    public void testToggleTrainerActive(){
        // Act
        trainerService.toggleTraineeActive(1L, new Credentials());
        // Assert
        verify(userService, times(1)).toggleActive(1L);
    }

    @Test
    public void testChangePassword(){
        // Act
        trainerService.changePassword("username", "oldPassword", "newPassword");
        // Assert
        verify(userService, times(1))
                .changePassword("username", "oldPassword", "newPassword");
    }
}
