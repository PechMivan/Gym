package com.gym.gym;

import com.gym.gym.entities.Training;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.storages.TrainingDataStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TrainingDataStorageTests {

    @Autowired
    TrainingDataStorage t1;

    @Test
    public void testWriteAndReadToFile() {

        // Test data
        Map<Long, Training> testData = new HashMap<>();
        Training training = Training.builder()
                        .trainingId(1L)
                        .traineeId(1L)
                        .trainerId(1L)
                        .trainingName("Super-Cardio")
                        .trainingType(TrainingType.CARDIO)
                        .trainingDate(new Date())
                        .trainingDuration(10)
                        .build();
        testData.put(1L, training);

        // Act
        t1.writeToFile(testData);
        Map<Long, Training> result = t1.readFromFile();

        // Assert
        assertEquals(testData, result);

    }

}
