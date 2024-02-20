package com.gym.gym;

import com.gym.gym.entities.*;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainingRepository;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import com.gym.gym.services.implementations.TrainerServiceImpl;
import com.gym.gym.services.implementations.TrainingServiceImpl;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TrainingServiceImplTests {

    @Mock
    TrainingRepository trainingRepository;
    @Mock
    TrainerServiceImpl trainerService;
    @Mock
    TraineeServiceImpl traineeService;

    @Mock
    Validator validator;

    List<Training>trainings;

    @InjectMocks
    TrainingServiceImpl trainingService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        this.trainings = Arrays.asList(new Training(), new Training(), new Training());
    }

    @Test
    public void testGetAllTrainings(){
        // Arrange
        when(trainingRepository.findAll()).thenReturn(trainings);
        // Act
        List<Training> trainingList = trainingService.getAllTrainings();
        // Assert
        assertEquals(trainings.size(), trainingList.size());
    }

    @Test
    public void testGetTrainingById(){
        // Arrange
        long trainingId = 1L;
        Training training = new Training();
        training.setId(trainingId);
        training.setTrainingName("test");
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));
        // Act
        Training result = trainingService.getTrainingById(trainingId);
        // Assert
        assertNotNull(result);
        assertEquals(training.getId(), result.getId());
        assertEquals(training.getTrainingName(), result.getTrainingName());
        assertThrows(NotFoundException.class, ()-> trainingService.getTrainingById(100L));
    }

    @Test
    public void saveTraining(){
        // Act
        trainingService.saveTraining(new Training());
        // Assert
        verify(trainingRepository, times(1)).save(new Training());
    }


    @Test
    public void testGetAllTrainersNotInTraineeTrainersListByUsername(){
        // Arrange
        Trainer trainer1 = Trainer.builder().user(new User()).id(1L).build();
        Trainer trainer2 = Trainer.builder().user(new User()).id(2L).build();
        Trainer trainer3 = Trainer.builder().user(new User()).id(3L).build();
        List<Trainer> trainersInTraineeList = new ArrayList<>(Arrays.asList(trainer1, trainer3));

        Trainee trainee = Trainee.builder().trainers(trainersInTraineeList).build();

        List<Trainer> allTrainers = new ArrayList<>(Arrays.asList(trainer1, trainer2, trainer3));
        when(traineeService.getTraineeByUsername(anyString())).thenReturn(trainee);
        when(trainerService.getAllTrainers()).thenReturn(allTrainers);
        // Act
        List<Trainer> trainersNotInTraineeList = trainingService.getAllTrainersNotInTraineeTrainersListByUsername("username");
        // Assert
        assertNotNull(trainersNotInTraineeList);
        assertEquals(1, trainersNotInTraineeList.size());
        assertTrue(trainersNotInTraineeList.contains(trainer2));
    }

    @Test
    public void testCreateTraining(){
        // Arrange

        User user = User.builder().username("username").build();

        Trainee trainee = Trainee.builder().user(user).build();
        Trainer trainer = Trainer.builder().user(user).build();

        TrainingType existingTrainingType = TrainingType.builder().id(1L).trainingTypeName("HIIT").build();
        Trainee existingTrainee = Trainee.builder().id(1L).user(user).build();
        Trainer existingTrainer = Trainer.builder().id(1L).user(user).specialization(existingTrainingType).build();

        Training training = Training.builder()
                                  .trainee(trainee)
                                  .trainer(trainer)
                                  .trainingName("trainingName")
                                  .trainingDate(new Date())
                                  .trainingDuration(10)
                                  .build();

        when(traineeService.getTraineeByUsername(training.getTrainee().getUser().getUsername())).thenReturn(existingTrainee);
        when(trainerService.getTrainerByUsername(training.getTrainer().getUser().getUsername())).thenReturn(existingTrainer);

        // Act

        Training result = trainingService.createTraining(training);

        // Assert
        assertNotNull(training);
        assertEquals(1L, result.getTrainee().getId());
        assertEquals(1L, result.getTrainer().getId());
        assertEquals(existingTrainingType.getTrainingTypeName(), result.getTrainingType().getTrainingTypeName());
        assertEquals(training.getTrainingName(), result.getTrainingName());
        assertEquals(training.getTrainingDuration(), result.getTrainingDuration());
    }

    @Test
    public void testGetTrainingsByTraineeUsernameAndBetweenDates(){
        // Arrange
        String username = "username";
        String startDate = "20-10-2025";
        String endDate = "15-05-2025";
        when(trainingRepository.findAllTrainingsByTraineeUsernameAndBetweenDates(anyString(), any(Date.class), any(Date.class))).thenReturn(trainings);
        // Act
        List<Training> trainingList = trainingService.getTrainingsByTraineeUsernameAndBetweenDates(username, startDate, endDate);
        // Assert
        assertNotNull(trainingList);
        assertEquals(3, trainingList.size());
    }

    @Test
    public void testGetTrainingsByTrainerUsernameAndBetweenDates(){
        // Arrange
        String username = "username";
        String startDate = "20-10-2025";
        String endDate = "15-05-2025";
        when(trainingRepository.findAllTrainingsByTrainerUsernameAndBetweenDates(anyString(), any(Date.class), any(Date.class))).thenReturn(trainings);
        // Act
        List<Training> trainingList = trainingService.getTrainingsByTrainerUsernameAndBetweenDates(username, startDate, endDate);
        // Assert
        assertNotNull(trainingList);
        assertEquals(3, trainingList.size());
    }

    @Test
    public void testGetByTraineeUsernameAndTrainerName(){
        // Act
        trainingService.getByTraineeUsernameAndTrainerName("traineeUsername", "trainerName");
        // Assert
        verify(trainingRepository, times(1)).findAllTrainingsByTraineeUsernameAndTrainerName("traineeUsername", "trainerName");
    }

    @Test
    public void testGetTrainingsByTraineeUsernameAndTrainingType(){
        // Act
        trainingService.getTrainingsByTraineeUsernameAndTrainingType("traineeUsername", "trainingTypeName");
        // Assert
        verify(trainingRepository, times(1)).findAllTrainingsByTraineeUsernameAndTrainingType("traineeUsername", "trainingTypeName");
    }

    @Test
    public void testGetTrainingsByTrainerUsernameAndTraineeName(){
        // Act
        trainingService.getTrainingsByTrainerUsernameAndTraineeName("trainerUsername", "traineeName");
        // Assert
        verify(trainingRepository, times(1)).findAllTrainingsByTrainerUsernameAndTraineeName("trainerUsername", "traineeName");
    }
}
