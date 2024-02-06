package com.gym.gym.storages;

import com.gym.gym.entities.Trainer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TrainerDataStorage extends DataStorage<Trainer> {

    private Map<Long, Trainer> trainerMap;

    public TrainerDataStorage(@Value("${trainerDataStorage.filepath}") String filepath){
        super(filepath, "trainer");
    }

    @PostConstruct
    public void customInit() {
        this.trainerMap = super.readFromFile();
    }

    public Map<Long, Trainer> getTrainerMap() {
        return trainerMap;
    }

    public void setTrainerMap(Map<Long, Trainer> trainerMap) {
        this.trainerMap = trainerMap;
    }

}
