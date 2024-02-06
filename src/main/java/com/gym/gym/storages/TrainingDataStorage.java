package com.gym.gym.storages;

import com.gym.gym.entities.Training;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Component
@SuppressWarnings("unused")
public class TrainingDataStorage extends DataStorage<Training> {

    private Map<Long, Training> trainingMap;

    public TrainingDataStorage(@Value("${trainingDataStorage.filepath}") String filepath){
        super(filepath, "training");
    }

    @PostConstruct
    public void customInit() {
        trainingMap = super.readFromFile();
    }

    public Map<Long, Training> getTrainingMap() {
        return trainingMap;
    }

    public void setTrainingMap(Map<Long, Training> trainingMap) {
        this.trainingMap = trainingMap;
    }

}
