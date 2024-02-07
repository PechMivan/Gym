package com.gym.gym;

import com.gym.gym.entities.Trainee;
import com.gym.gym.storages.TraineeDataStorage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraineeDataStorageTests {

    @Autowired
    TraineeDataStorage t1;

    @Test
    public void testWriteAndReadToFile() {

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
        assertEquals(testData, result);

    }

}
