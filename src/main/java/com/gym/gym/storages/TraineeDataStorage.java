package com.gym.gym.storages;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
@SuppressWarnings("unused")
public class TraineeDataStorage extends DataStorage {

    @Value("$trainee.data.storage.filepath")
    private String filepath;
    private Map<Long, Object> traineeMap;

    @PostConstruct
    public void init() {
        this.traineeMap = super.readFromFile(filepath);
    }
}
