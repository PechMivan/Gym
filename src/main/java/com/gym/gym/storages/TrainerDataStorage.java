package com.gym.gym.storages;

import com.gym.gym.entities.Trainer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TrainerDataStorage extends DataStorage {

    @Value("${trainerDataStorage.filepath}")
    private String filepath;
    private Map<Long, Object> trainerMap;

    @PostConstruct
    public void customInit() {
        this.trainerMap = super.readFromFile(filepath);
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Map<Long, Object> getTrainerMap() {
        return trainerMap;
    }

    public void setTrainerMap(Map<Long, Object> trainerMap) {
        this.trainerMap = trainerMap;
    }
}
