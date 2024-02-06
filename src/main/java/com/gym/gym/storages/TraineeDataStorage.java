package com.gym.gym.storages;

import com.gym.gym.entities.Trainee;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@SuppressWarnings("unused")
public class TraineeDataStorage extends DataStorage<Trainee> {

    private Map<Long, Trainee> traineeMap;

    public TraineeDataStorage(@Value("${traineeDataStorage.filepath}") String filepath){
        super(filepath, "trainee");
    }

    @PostConstruct
    public void init() {
        this.traineeMap = super.readFromFile();
    }

    public Map<Long, Trainee> getTraineeMap() {
        return traineeMap;
    }

    public void setTraineeMap(Map<Long, Trainee> traineeMap) {
        this.traineeMap = traineeMap;
    }
}
