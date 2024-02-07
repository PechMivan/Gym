package com.gym.gym;
import static org.mockito.Mockito.*;

import com.gym.gym.daos.implementations.TrainerDAOImpl;
import com.gym.gym.entities.Trainer;
import com.gym.gym.services.implementations.TrainerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        long id = 1L;
        Trainer trainer = new Trainer();
        trainer.setUserId(id);

        when(trainerDAO.findById(id)).thenReturn(Optional.of(trainer));

        Trainer result = trainerService.getTrainerById(id);

        assertEquals(trainer, result);
    }

    @Test
     void getTrainerById_NotExists_ThrowsException() {
        long id = 1L;

        when(trainerDAO.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> trainerService.getTrainerById(id));
    }

    @Test
     void getAllTrainers() {
        List<Trainer> trainers = new ArrayList<>();
        trainers.add(new Trainer());
        trainers.add(new Trainer());

        when(trainerDAO.findAll()).thenReturn(trainers);

        List<Trainer> result = trainerService.getAllTrainers();

        assertEquals(trainers, result);
    }

    @Test
     void createTrainer() {
        Trainer trainer = new Trainer();
        trainer.setFirstName("John");
        trainer.setLastName("Doe");

        when(trainerService.getAllTrainers()).thenReturn(new ArrayList<>());

        Trainer result = trainerService.createTrainer(trainer);

        assertNotNull(result.getUsername());
        assertNotNull(result.getPassword());
        verify(trainerDAO, times(1)).save(trainer);
    }

    @Test
     void createUsername() {
        // Mocking existing trainers with similar username
        Trainer trainer1 = new Trainer();
        trainer1.setFirstName("John");
        trainer1.setLastName("Doe");
        trainer1.setUsername("John.Doe");

        Trainer trainer2 = new Trainer();
        trainer2.setFirstName("John");
        trainer2.setLastName("Doe");
        trainer2.setUsername("John.Doe1");

        List<Trainer> existingTrainers = new ArrayList<>();
        existingTrainers.add(trainer1);
        existingTrainers.add(trainer2);

        when(trainerService.getAllTrainers()).thenReturn(existingTrainers);

        // Test
        String username = trainerService.createUsername("John", "Doe");
        assertEquals("John.Doe2", username); // Expecting "John.Doe2" as the username
    }

    @Test
     void createPassword() {
        // Test
        String password = trainerService.createPassword();
        assertEquals(10, password.length()); // Expecting a password of length 10
    }

    @Test
     void updateTrainer() {
        // Mock existing trainer
        long id = 1L;
        Trainer existingTrainer = new Trainer();
        existingTrainer.setUserId(id);

        // Mock updated trainer
        Trainer updatedTrainer = new Trainer();
        updatedTrainer.setFirstName("Updated");

        // Mock behavior of DAO
        when(trainerDAO.findById(id)).thenReturn(Optional.of(existingTrainer));

        // Test
        trainerService.updateTrainer(id, updatedTrainer);

        assertEquals("Updated", existingTrainer.getFirstName()); // Expecting the first name to be updated
        verify(trainerDAO, times(1)).save(existingTrainer); // Expecting save method to be called
    }

    @Test
     void deleteTrainer() {
        // Mock
        long id = 1L;

        // Test
        trainerService.deleteTrainer(id);

        verify(trainerDAO, times(1)).delete(id); // Expecting delete method to be called with given id
    }
}
