package com.gym.gym.storages;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@SuppressWarnings("unused")
public abstract class DataStorage<T> {

    private final String STORAGE_TYPE;
    private final String FILEPATH;

    protected DataStorage(String FILEPATH, String STORAGE_TYPE){
        this.FILEPATH = FILEPATH;
        this.STORAGE_TYPE = STORAGE_TYPE;
    }

    public void writeToFile(Map<Long, T> data) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILEPATH))) {
            outputStream.writeObject(data);
            // Add logging here if necessary
        } catch (IOException e) {
            throw new RuntimeException("Error writing data to file: " + e.getMessage(), e);
        }
    }

    public Map<Long, T> readFromFile() {
        Map<Long, T> data = new HashMap<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILEPATH))) {

            Object object = inputStream.readObject();

            if (object instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<Long, T> map = (Map<Long, T>) object;
                data.putAll(map);
                // Log the successful read
            } else {
                System.out.println("Data file is corrupted.");
            }

        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Error deserializing object: " + e.getMessage());
        }
        return data;
    }
}
