package com.gym.gym;

import com.gym.gym.entities.Training;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.repositories.TrainingTypeRepository;
import com.gym.gym.services.implementations.TrainingTypeHibernateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TrainingTypeHibernateServiceImplTests {

    @Mock
    TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    TrainingTypeHibernateServiceImpl trainingTypeHibernateService;

    TrainingType trainingType;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        this.trainingType = TrainingType.builder()
                                        .id(1L)
                                        .trainingTypeName("HIIT")
                                        .build();
    }

    @Test
    public void testGetAllTrainingTypes(){
        // Arrange
        List<TrainingType> trainingTypes = Arrays.asList(new TrainingType(), new TrainingType(), new TrainingType());
        when(trainingTypeRepository.findAll()).thenReturn(trainingTypes);
        // Act
        List<TrainingType> trainingTypeList = trainingTypeHibernateService.getAllTrainingTypes();
        // Assert
        assertEquals(trainingTypes.size(), trainingTypeList.size());
    }

    @Test
    public void testGetTrainingTypeById(){
        // Arrange
        long trainingTypeId = 1L;
        when(trainingTypeRepository.findById(trainingTypeId)).thenReturn(Optional.of(trainingType));
        // Act
        TrainingType result = trainingTypeHibernateService.getTrainingTypeById(trainingTypeId);
        // Assert
        assertNotNull(result);
        assertEquals("HIIT", result.getTrainingTypeName());
    }

    @Test
    public void testGetTrainingTypeByName(){
        // Arrange
        String name = "name";
        when(trainingTypeRepository.findByTrainingTypeName(name)).thenReturn(Optional.of(trainingType));
        // Act
        TrainingType result = trainingTypeHibernateService.getTrainingTypeByName(name);
        // Assert
        assertNotNull(result);
        assertEquals("HIIT", result.getTrainingTypeName());
    }
}