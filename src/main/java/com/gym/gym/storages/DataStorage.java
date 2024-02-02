package com.gym.gym.storages;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
@SuppressWarnings("unused")
public abstract class DataStorage {

    public void writeToFile(String filepath, Map<Long, Object> data){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filepath));
            outputStream.writeObject(data);
            //Aqui va un log
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Map<Long, Object> readFromFile(String filepath){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filepath));

            Map<Long, Object> data = null;
            Object object = inputStream.readObject();

            if (object instanceof Map){
                data = (HashMap<Long, Object>) object;
                //Aqui va un log
            }
            else {
                System.out.println("Data file corrupted...");
            }

            return data;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
