package com.gym.gym;

import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.storages.TrainerDataStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrainerDataStorageTests {

    @Autowired
    TrainerDataStorage t1;

    @Test
    public void testWriteAndReadToFile() {

        // Test data
        Map<Long, Trainer> testData = new HashMap<>();
        Trainer newTrainer = Trainer.builder()
                .firstName("Mario")
                .lastName("Pech")
                .username("Mapech")
                .password("pass")
                .specialization(TrainingType.YOGA)
                .isActive(true)
                .userId(1L)
                .build();

        testData.put(1L, newTrainer);

        // Act
        t1.writeToFile(testData);
        Map<Long, Trainer> result = t1.readFromFile();

        // Assert
        assertEquals(testData, result);

    }

}

