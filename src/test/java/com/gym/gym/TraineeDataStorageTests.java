package com.gym.gym;

import com.gym.gym.entities.Trainee;
import com.gym.gym.storages.TraineeDataStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@SpringBootTest
 class TraineeDataStorageTests {

    @Autowired
    TraineeDataStorage t1;

    @Test
     void testWriteAndReadToFile() {

        // Test data
        Map<Long, Trainee> testData = new HashMap<>();
        Trainee newTrainee = Trainee.builder()
                .firstName("Mario")
                .lastName("Pech")
                .address("Street 20")
                .username("Mapech")
                .password("pass")
                .dateOfBirth(new Date())
                .isActive(true)
                .userId(1L)
                .build();

        System.out.println(newTrainee.toString());
        testData.put(1L, newTrainee);

        // Act
        t1.writeToFile(testData);
        Map<Long, Trainee> result = t1.readFromFile();

        // Assert
        Assertions.assertEquals(testData, result);

    }

}
