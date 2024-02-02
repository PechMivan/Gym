package com.gym.gym;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataStorageIntegrationTest {

    @Rule
    public TemporaryFolder temporaryFolder = new TemporaryFolder();

    @Test
    public void testWriteAndReadToFile() {
        // Arrange
        DataStorage dataStorage = new DataStorageImpl();

        // Create a temporary file
        File tempFile = null;
        try {
            tempFile = temporaryFolder.newFile("test-file.dat");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Test data
        Map<Long, Object> testData = new HashMap<>();
        testData.put(1L, "Test Data");

        // Act
        dataStorage.writeToFile(tempFile.getAbsolutePath(), testData);
        Map<Long, Object> result = dataStorage.readFromFile(tempFile.getAbsolutePath());

        // Assert
        assertEquals(testData, result);

        // Cleanup: Optional, as TemporaryFolder takes care of it
        // tempFile.delete();
    }

    @Test
    public void testReadFromNonExistentFile() {
        // Arrange
        DataStorage dataStorage = new DataStorageImpl();

        // Create a non-existent file path
        String nonExistentFilePath = temporaryFolder.getRoot().getAbsolutePath() + "/non-existent-file.dat";

        // Act
        Map<Long, Object> result = dataStorage.readFromFile(nonExistentFilePath);

        // Assert
        assertNull(result);
    }

    // Additional tests can be added as needed
}
