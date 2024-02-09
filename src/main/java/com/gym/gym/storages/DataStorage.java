package com.gym.gym.storages;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
@Getter
@SuppressWarnings("unused")
public abstract class DataStorage<T> {

    Logger logger = LoggerFactory.getLogger(DataStorage.class);
    private final String storageType;
    private final String filepath;

    /* filepath is provided by the bean instance once it's created.
    The actual path is injected to the bean through the value annotation;
    final path can be found at application.properties file. */
    protected DataStorage(String filepath, String storageType){
        this.storageType = storageType;
        this.filepath = filepath;
    }

    public void writeToFile(Map<Long, T> data) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filepath))) {
            outputStream.writeObject(data);
            logger.info("Data successfully written to file.");
        } catch (IOException e) {
            throw new RuntimeException("Error writing data to file: " + e.getMessage(), e); //NOSONAR
        }
    }

    public Map<Long, T> readFromFile() {
        Map<Long, T> data = new HashMap<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filepath))) {

            Object object = inputStream.readObject();

            if (object instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<Long, T> map = (Map<Long, T>) object;
                data.putAll(map);
                logger.info("Data successfully read from file.");
            } else {
                logger.error("Data file is corrupted!");
            }

        } catch (IOException e) {
            logger.error("Error reading from file: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            logger.error("Error deserializing object: " + e.getMessage());
        }
        return data;
    }
}
