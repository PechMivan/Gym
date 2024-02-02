package com.gym.gym.storages;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@SuppressWarnings("unused")
public class TraineeDataStorage extends DataStorage {

    @Value("${trainingDataStorage.filepath}")
    private String filepath;
    private Map<Long, Object> traineeMap;

    @PostConstruct
    public void init() {
        this.traineeMap = super.readFromFile(filepath);
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Map<Long, Object> getTraineeMap() {
        return traineeMap;
    }

    public void setTraineeMap(Map<Long, Object> traineeMap) {
        this.traineeMap = traineeMap;
    }
}
