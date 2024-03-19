package com.gym.gym;

import com.gym.gym.clients.WorkloadServiceClient;
import com.gym.gym.entities.*;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainingRepository;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import com.gym.gym.services.implementations.TrainerServiceImpl;
import com.gym.gym.services.implementations.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceImplTests {

    @Mock
    TrainingRepository trainingRepository;
    @Mock
    TrainerServiceImpl trainerService;
    @Mock
    TraineeServiceImpl traineeService;
    @Mock
    WorkloadServiceClient workloadServiceClient;

    List<Training>trainings;

    @InjectMocks
    TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        this.trainings = Arrays.asList(new Training(), new Training(), new Training());
    }

    @Test
    void getAllTrainings_withTrainings_succesful(){
        // Arrange
        when(trainingRepository.findAll()).thenReturn(trainings);
        // Act
        List<Training> trainingList = trainingService.getAllTrainings();
        // Assert
        assertEquals(trainings.size(), trainingList.size());
    }

    @Test
    void getAllTrainings_withoutTrainings_returnsEmptyList(){
        // Arrange
        List<Training> emptyTrainingList = Collections.emptyList();
        when(trainingRepository.findAll()).thenReturn(emptyTrainingList);
        // Act
        List<Training> result = trainingService.getAllTrainings();
        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getTraining_validId_successful(){
        // Arrange
        long trainingId = 1L;
        Training training = new Training();
        training.setId(trainingId);
        training.setName("test");
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));
        // Act
        Training result = trainingService.getTrainingById(trainingId);
        // Assert
        assertNotNull(result);
        assertEquals(training.getId(), result.getId());
        assertEquals(training.getName(), result.getName());
    }

    @Test
    void getTraining_invalidId_throwsNotFoundException(){
        // Act and Arrange
        assertThrows(NotFoundException.class, ()-> trainingService.getTrainingById(100L));
    }

    @Test
    void saveTraining(){
        // Act
        trainingService.saveTraining(new Training());
        // Assert
        verify(trainingRepository, times(1)).save(new Training());
    }


    @Test
    void testGetAllTrainersNotInTraineeTrainersListByUsername(){
        // Arrange

        User userActive = User.builder().isActive(true).build();
        User userNotActive = User.builder().isActive(false).build();

        Trainer trainer1 = Trainer.builder().user(userActive).id(1L).build();
        Trainer trainer2 = Trainer.builder().user(userActive).id(2L).build();
        Trainer trainer3 = Trainer.builder().user(userActive).id(3L).build();
        Trainer trainer4 = Trainer.builder().user(userNotActive).id(3L).build();
        List<Trainer> trainersInTraineeList = new ArrayList<>(Arrays.asList(trainer1, trainer3));

        Trainee trainee = Trainee.builder().trainers(trainersInTraineeList).build();

        List<Trainer> allTrainers = new ArrayList<>(Arrays.asList(trainer1, trainer2, trainer3, trainer4));
        when(traineeService.getTraineeByUsername(anyString())).thenReturn(trainee);
        when(trainerService.getAllTrainers()).thenReturn(allTrainers);
        // Act
        List<Trainer> trainersNotInTraineeList = trainingService.getAllTrainersNotInTraineeTrainersListByUsername("username");
        // Assert
        assertNotNull(trainersNotInTraineeList);
        assertEquals(1, trainersNotInTraineeList.size());
        assertTrue(trainersNotInTraineeList.contains(trainer2));
        assertFalse(trainersNotInTraineeList.contains(trainer4));
    }

    @Test
    void createTraining(){
        // Arrange
        User user = User.builder().username("username").build();

        Trainee trainee = Trainee.builder().user(user).build();
        Trainer trainer = Trainer.builder().user(user).build();

        TrainingType existingTrainingType = TrainingType.builder().id(1L).name("HIIT").build();
        Trainee existingTrainee = Trainee.builder().id(1L).user(user).build();
        Trainer existingTrainer = Trainer.builder().id(1L).user(user).specialization(existingTrainingType).build();

        Training training = Training.builder()
                                  .trainee(trainee)
                                  .trainer(trainer)
                                  .name("name")
                                  .date(new Date())
                                  .duration(10)
                                  .build();

        when(traineeService.getTraineeByUsername(training.getTrainee().getUser().getUsername())).thenReturn(existingTrainee);
        when(trainerService.getTrainerByUsername(training.getTrainer().getUser().getUsername())).thenReturn(existingTrainer);

        // Act

        Training result = trainingService.createTraining(training);

        // Assert
        assertNotNull(training);
        assertEquals(1L, result.getTrainee().getId());
        assertEquals(1L, result.getTrainer().getId());
        assertEquals(existingTrainingType.getName(), result.getTrainingType().getName());
        assertEquals(training.getName(), result.getName());
        assertEquals(training.getDuration(), result.getDuration());
    }


}
