package com.gym.gym;

import com.gym.gym.storages.DataStorage;
import com.gym.gym.storages.DataStorageManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

 class DataStorageManagerTests {

    @Mock
    private DataStorage<Object> dataStorageMock;

    @InjectMocks
    private DataStorageManager dataStorageManager;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testWrite() {
        // Arrange
        String storageType = "testType";
        Map<Long, Object> testData = new HashMap<>();

        when(dataStorageMock.getSTORAGE_TYPE()).thenReturn(storageType);
        when(dataStorageMock.readFromFile()).thenReturn(new HashMap<>());

        dataStorageManager.setMainDataStorage(List.of(dataStorageMock));

        // Act
        dataStorageManager.write(storageType, testData);

        // Assert
        verify(dataStorageMock, times(1)).writeToFile(testData);
    }

    @Test
     void testRead() {
        // Arrange
        String storageType = "testType";
        Map<Long, Object> expectedData = new HashMap<>();

        when(dataStorageMock.getSTORAGE_TYPE()).thenReturn(storageType);
        when(dataStorageMock.readFromFile()).thenReturn(expectedData);

        dataStorageManager.setMainDataStorage(List.of(dataStorageMock));

        // Act
        Map<Long, Object> result = dataStorageManager.read(storageType);

        // Assert
        assertEquals(expectedData, result);
        verify(dataStorageMock, times(1)).readFromFile();
    }

    @Test
     void testWriteWithUnknownStorageType() {
        // Arrange
        String unknownStorageType = "unknownType";
        Map<Long, Object> testData = new HashMap<>();

        // Act
        dataStorageManager.write(unknownStorageType, testData);

        // Assert
        // Verify that no interactions occurred with the dataStorageMock
        verify(dataStorageMock, never()).writeToFile(testData);
    }

    @Test
     void testReadWithUnknownStorageType() {
        // Arrange
        String unknownStorageType = "unknownType";

        // Act
        Map<Long, Object> result = dataStorageManager.read(unknownStorageType);

        // Assert
        // Verify that no interactions occurred with the dataStorageMock
        verify(dataStorageMock, never()).readFromFile();

        // Ensure the result is an empty map
        assertEquals(new HashMap<>(), result);
    }
}

