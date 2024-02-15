package com.gym.gym;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.dtos.UserDTO;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainerRepository;
import com.gym.gym.services.implementations.TrainerHibernateServiceImpl;
import com.gym.gym.services.implementations.TrainingTypeHibernateServiceImpl;
import com.gym.gym.services.implementations.UserHibernateServiceImpl;
import jakarta.validation.Validator;
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

public class TrainerHibernateServiceImplTests {

    @Mock
    UserHibernateServiceImpl userHibernateService;

    @Mock
    TrainingTypeHibernateServiceImpl trainingTypeHibernateService;

    @Mock
    Validator validator;

    @Mock
    TrainerRepository trainerRepository;

    @InjectMocks
    TrainerHibernateServiceImpl trainerHibernateService;

    User user;
    UserDTO userDTO;
    TrainingType trainingType;
    Trainer trainer;
    TrainerDTO trainerDTO;


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

        this.userDTO = UserDTO.builder()
                              .firstname("John")
                              .lastname("Doe")
                              .username("John.Doe")
                              .password("passtest")
                              .build();

        this.trainerDTO = TrainerDTO.builder()
                                    .userDTO(userDTO)
                                    .specialization("HIIT").build();
    }

    @Test
    public void testGetAllTrainers(){
        // Arrange
        List<Trainer> trainers = Arrays.asList(new Trainer(), new Trainer());
        when(trainerRepository.findAll()).thenReturn(trainers);
        // Act
        List<Trainer> trainersList = trainerHibernateService.getAllTrainers();
        // Assert
        assertEquals(trainers.size(), trainersList.size());
    }

    @Test
    public void testGetTrainerById(){
        // Arrange
        long trainerId = 1L;
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainer));
        // Act
        Trainer result = trainerHibernateService.getTrainerById(1L);
        // Assert
        assertNotNull(result);
        assertEquals(trainerId, result.getId());
        assertEquals("John", result.getUser().getFirstName());
        assertThrows(NotFoundException.class, () ->trainerHibernateService.getTrainerById(100L));
    }

    @Test
    public void testGetTrainerByUsername(){
        // Arrange
        String username = "John.Doe";
        when(trainerRepository.findByUserUsername(username)).thenReturn(Optional.of(trainer));
        // Act
        Trainer result = trainerHibernateService.getTrainerByUsername(username);
        // Assert
        assertNotNull(trainer);
        assertEquals(username, result.getUser().getUsername());
        assertEquals("John", result.getUser().getFirstName());
        assertThrows(NotFoundException.class, () ->trainerHibernateService.getTrainerByUsername("wrongUsername"));
    }

    @Test
    public void testCreateTrainer(){
        // Arrange
        when(userHibernateService.createUser(trainerDTO.userDTO)).thenReturn(user);
        when(trainingTypeHibernateService.getTrainingTypeByName(trainerDTO.specialization)).thenReturn(trainingType);
        // Act
        Trainer createdTrainer = trainerHibernateService.createTrainer(trainerDTO);
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
        trainerHibernateService.saveTrainer(trainer);
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

        UserDTO userDTO = UserDTO.builder()
                .firstname("testName")
                .build();

        TrainerDTO trainerDTOUpdated = TrainerDTO.builder()
                .userDTO(userDTO)
                .specialization("CARDIO").build();

        when(userHibernateService.updateUser(trainerDTOUpdated.userDTO)).thenReturn(userUpdated);
        when(trainingTypeHibernateService.getTrainingTypeByName(trainerDTOUpdated.specialization)).thenReturn(trainingTypeUpdated);
        when(trainerRepository.findById(1L)).thenReturn(Optional.of(trainer));
        // Act
        Trainer updatedTrainer = trainerHibernateService.updateTrainer(1L, trainerDTOUpdated);
        // Assert
        assertEquals("testName", updatedTrainer.getUser().getFirstName());
        assertEquals(trainerDTOUpdated.specialization, updatedTrainer.getSpecialization().getTrainingTypeName());
    }


    @Test
    public void testToggleTrainerActive(){
        // Act
        trainerHibernateService.toggleTraineeActive(1L, new Credentials());
        // Assert
        verify(userHibernateService, times(1)).toggleActive(1L);
    }

    @Test
    public void testChangePassword(){
        // Act
        trainerHibernateService.changePassword("username", "oldPassword", "newPassword");
        // Assert
        verify(userHibernateService, times(1))
                .changePassword("username", "oldPassword", "newPassword");
    }
}
