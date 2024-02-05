package com.gym.gym.storages;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public abstract class DataStorage {

    private final String storageType;
    private final String FILEPATH;

    protected DataStorage(String FILEPATH, String storageType){
        this.FILEPATH = FILEPATH;
        this.storageType = storageType;
    }

    public void writeToFile(Map<Long, Object> data){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(FILEPATH));
            outputStream.writeObject(data);
            //Aqui va un log
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Long, Object> readFromFile() {

        Map<Long, Object> data = new HashMap<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(FILEPATH));

            Object object = inputStream.readObject();

            if (object instanceof Map) {
                data = (HashMap<Long, Object>) object;
                //Aqui va un log
            } else {
                System.out.println("Data file corrupted...");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }

        return data;
    }

    public String getStorageType() {
        return storageType;
    }

    public String getFILEPATH() {
        return FILEPATH;
    }
}
