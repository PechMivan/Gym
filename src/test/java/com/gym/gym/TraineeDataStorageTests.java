package com.gym.gym;

import com.gym.gym.entities.Trainee;
import com.gym.gym.storages.TraineeDataStorage;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TraineeDataStorageTests {

    @Autowired
    TraineeDataStorage t1;

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testWriteAndReadToFile() {

        File tempFile = null;
        try {
            tempFile = temporaryFolder.newFile("test-file.dat");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

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
        assertThat(result).containsExactlyEntriesOf(testData);

        // Cleanup: Optional, as TemporaryFolder takes care of it
        // tempFile.delete();
    }

    // Additional tests can be added as needed
}
