package com.gym.gym.storages;

import com.gym.gym.entities.Trainer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TrainerDataStorage extends DataStorage {

    private Map<Long, Object> trainerMap;

    public TrainerDataStorage(@Value("${trainingDataStorage.filepath}") String filepath){
        super(filepath, "trainer");
    }

    @PostConstruct
    public void customInit() {
        this.trainerMap = super.readFromFile();
    }

    public Map<Long, Object> getTrainerMap() {
        return trainerMap;
    }

    public void setTrainerMap(Map<Long, Object> trainerMap) {
        this.trainerMap = trainerMap;
    }

}
