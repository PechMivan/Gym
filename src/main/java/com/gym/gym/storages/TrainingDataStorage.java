package com.gym.gym.storages;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@SuppressWarnings("unused")
public class TrainingDataStorage extends DataStorage {

    @Value("${trainingDataStorage.filepath}")
    private String filepath;
    private Map<Long, Object> trainingMap;

    @PostConstruct
    public void customInit() {
        trainingMap = super.readFromFile(filepath);
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Map<Long, Object> getTrainingMap() {
        return trainingMap;
    }

    public void setTrainingMap(Map<Long, Object> trainingMap) {
        this.trainingMap = trainingMap;
    }
}
