package com.gym.gym;

import com.gym.gym.entities.TrainingType;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainingTypeRepository;
import com.gym.gym.services.implementations.TrainingTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TrainingTypeServiceImplTests {

    @Mock
    TrainingTypeRepository trainingTypeRepository;

    @InjectMocks
    TrainingTypeServiceImpl trainingTypeService;

    TrainingType trainingType;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);

        this.trainingType = TrainingType.builder()
                                        .id(1L)
                                        .name("HIIT")
                                        .build();
    }

    @Test
    public void getAllTrainingTypes_withTrainingTypes_successful(){
        // Arrange
        List<TrainingType> trainingTypes = Arrays.asList(new TrainingType(), new TrainingType(), new TrainingType());
        when(trainingTypeRepository.findAll()).thenReturn(trainingTypes);
        // Act
        List<TrainingType> result = trainingTypeService.getAllTrainingTypes();
        // Assert
        assertEquals(trainingTypes.size(), result.size());
    }

    @Test
    public void getAll_withoutTrainingTypes_returnsEmptyList(){
        // Arrange
        List<TrainingType> trainingTypes = Collections.emptyList();
        when(trainingTypeRepository.findAll()).thenReturn(trainingTypes);
        // Act
        List<TrainingType> result = trainingTypeService.getAllTrainingTypes();
        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void getTrainingTypeById(){
        // Arrange
        long trainingTypeId = 1L;
        when(trainingTypeRepository.findById(trainingTypeId)).thenReturn(Optional.of(trainingType));
        // Act
        TrainingType result = trainingTypeService.getTrainingTypeById(trainingTypeId);
        // Assert
        assertNotNull(result);
        assertEquals("HIIT", result.getName());
    }

    @Test
    public void getTrainingType_validId_successful(){
        // Arrange
        long trainingTypeId = 1L;
        when(trainingTypeRepository.findById(trainingTypeId)).thenReturn(Optional.of(trainingType));
        // Act
        TrainingType result = trainingTypeService.getTrainingTypeById(trainingTypeId);
        // Assert
        assertNotNull(result);
        assertEquals("HIIT", result.getName());
    }

    @Test
    public void getTrainingType_invalidId_throwsNotFoundException(){
        // Act and Arrange
        assertThrows(NotFoundException.class, () -> trainingTypeService.getTrainingTypeById(100L));
    }

    @Test
    public void getTrainingType_validName_successful(){
        // Arrange
        String name = "name";
        when(trainingTypeRepository.findByName(name)).thenReturn(Optional.of(trainingType));
        // Act
        TrainingType result = trainingTypeService.getTrainingTypeByName(name);
        // Assert
        assertNotNull(result);
        assertEquals("HIIT", result.getName());
    }

    @Test
    public void getTrainingType_invalidName_throwsNotFoundException(){
        // Act and Assert
        assertThrows(NotFoundException.class, () -> trainingTypeService.getTrainingTypeByName("wrongName"));
    }
}
