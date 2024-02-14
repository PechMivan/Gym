package com.gym.gym;

import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.entities.User;
import com.gym.gym.storages.TrainerDataStorage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
 class TrainerDataStorageTests {

    @Autowired
    TrainerDataStorage t1;

    @Test
     void testWriteAndReadToFile() {

        // Arrange
        Map<Long, Trainer> testData = new HashMap<>();
        Trainer newTrainer = Trainer.builder()
                .id(1L)
                .user(new User())
                .build();

        testData.put(1L, newTrainer);

        // Act
        t1.writeToFile(testData);
        Map<Long, Trainer> result = t1.readFromFile();

        // Assert
        assertEquals(testData, result);

    }

}

