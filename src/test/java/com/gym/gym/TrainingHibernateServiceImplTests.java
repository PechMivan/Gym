package com.gym.gym;

import com.gym.gym.dtos.TrainingDTO;
import com.gym.gym.entities.*;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainingRepository;
import com.gym.gym.services.implementations.TraineeHibernateServiceImpl;
import com.gym.gym.services.implementations.TrainerHibernateServiceImpl;
import com.gym.gym.services.implementations.TrainingHibernateServiceImpl;
import com.gym.gym.services.implementations.TrainingTypeHibernateServiceImpl;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TrainingHibernateServiceImplTests {

    @Mock
    TrainingRepository trainingRepository;
    @Mock
    TrainingTypeHibernateServiceImpl trainingTypeHibernateService;
    @Mock
    TrainerHibernateServiceImpl trainerHibernateService;
    @Mock
    TraineeHibernateServiceImpl traineeHibernateService;
    @Mock
    Validator validator;

    List<Training>trainings;

    @InjectMocks
    TrainingHibernateServiceImpl trainingHibernateService;

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
        List<Training> trainingList = trainingHibernateService.getAllTrainings();
        // Assert
        assertEquals(trainings.size(), trainingList.size());
    }

    @Test
    public void testGetTrainingById(){
        // Arrange
        long trainingId = 1L;
        Training training = new Training();
        training.setId(1L);
        training.setTrainingName("test");
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
        // Act
        Training result = trainingHibernateService.getTrainingById(1L);
        // Assert
        assertNotNull(result);
        assertEquals(training.getId(), result.getId());
        assertEquals(training.getTrainingName(), result.getTrainingName());
        assertThrows(NotFoundException.class, ()-> trainingHibernateService.getTrainingById(100L));
    }

    @Test
    public void saveTraining(){
        // Act
        trainingHibernateService.saveTraining(new Training());
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

        List<Trainer> allTrainers = Arrays.asList(trainer1, trainer2, trainer3);
        when(traineeHibernateService.getTraineeByUsername(anyString())).thenReturn(trainee);
        when(trainerHibernateService.getAllTrainers()).thenReturn(new ArrayList<>(Arrays.asList(trainer1, trainer2, trainer3)));
        // Act
        List<Trainer> trainersNotInTraineeList = trainingHibernateService.getAllTrainersNotInTraineeTrainersListByUsername("username");
        // Assert
        assertNotNull(trainersNotInTraineeList);
        assertEquals(1, trainersNotInTraineeList.size());
        assertTrue(trainersNotInTraineeList.contains(trainer2));
    }

    @Test
    public void testCreateTraining(){
        // Arrange
        TrainingDTO trainingDTO = TrainingDTO.builder()
                                  .traineeId(1L)
                                  .trainerId(1L)
                                  .trainingTypeId(1L)
                                  .trainingName("trainingName")
                                  .trainingDate("2025-10-10")
                                  .trainingDuration(10.5f)
                                  .build();

        when(traineeHibernateService.getTraineeById(trainingDTO.traineeId)).thenReturn(new Trainee());
        when(trainerHibernateService.getTrainerById(trainingDTO.trainerId)).thenReturn(new Trainer());
        when(trainingTypeHibernateService.getTrainingTypeById(trainingDTO.trainingTypeId)).thenReturn(new TrainingType());

        // Act

        Training training = trainingHibernateService.createTraining(trainingDTO);

        // Assert
        assertNotNull(training);
        assertEquals(trainingDTO.trainingName, training.getTrainingName());
        assertEquals(trainingDTO.trainingDuration, training.getTrainingDuration());
    }

    @Test
    public void testGetTrainingsByTraineeUsernameAndBetweenDates(){
        // Arrange
        String username = "username";
        String startDate = "20-10-2025";
        String endDate = "15-05-2025";
        when(trainingRepository.findAllTrainingsByTraineeUsernameAndBetweenDates(anyString(), any(Date.class), any(Date.class))).thenReturn(trainings);
        // Act
        List<Training> trainingList = trainingHibernateService.getTrainingsByTraineeUsernameAndBetweenDates(username, startDate, endDate);
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
        List<Training> trainingList = trainingHibernateService.getTrainingsByTrainerUsernameAndBetweenDates(username, startDate, endDate);
        // Assert
        assertNotNull(trainingList);
        assertEquals(3, trainingList.size());
    }

    @Test
    public void testGetByTraineeUsernameAndTrainerName(){
        // Act
        List<Training> trainingList = trainingHibernateService.getByTraineeUsernameAndTrainerName("traineeUsername", "trainerName");
        // Assert
        verify(trainingRepository, times(1)).findAllTrainingsByTraineeUsernameAndTrainerName("traineeUsername", "trainerName");
    }

    @Test
    public void testGetTrainingsByTraineeUsernameAndTrainingType(){
        // Act
        List<Training> trainingList = trainingHibernateService.getTrainingsByTraineeUsernameAndTrainingType("traineeUsername", "trainingTypeName");
        // Assert
        verify(trainingRepository, times(1)).findAllTrainingsByTraineeUsernameAndTrainingType("traineeUsername", "trainingTypeName");
    }

    @Test
    public void testGetTrainingsByTrainerUsernameAndTraineeName(){
        // Act
        List<Training> trainingList = trainingHibernateService.getTrainingsByTrainerUsernameAndTraineeName("trainerUsername", "traineeName");
        // Assert
        verify(trainingRepository, times(1)).findAllTrainingsByTrainerUsernameAndTraineeName("trainerUsername", "traineeName");
    }
}
