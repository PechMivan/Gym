package com.gym.gym.storages;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@SuppressWarnings("unused")
public class TraineeDataStorage extends DataStorage {

    private Map<Long, Object> traineeMap;

    public TraineeDataStorage(@Value("${traineeDataStorage.filepath}") String filepath){
        super(filepath, "trainee");
    }

    @PostConstruct
    public void init() {
        this.traineeMap = super.readFromFile();
    }

    public Map<Long, Object> getTraineeMap() {
        return traineeMap;
    }

    public void setTraineeMap(Map<Long, Object> traineeMap) {
        this.traineeMap = traineeMap;
    }
}
