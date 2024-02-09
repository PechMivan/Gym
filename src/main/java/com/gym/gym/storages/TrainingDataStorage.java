package com.gym.gym.storages;

import com.gym.gym.entities.Training;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Setter
@Getter
@Component
@SuppressWarnings("unused")
public class TrainingDataStorage extends DataStorage<Training> {

    private Map<Long, Training> trainingMap;

    //Actual filepath is retrieved from application.properties.
    public TrainingDataStorage(@Value("${trainingDataStorage.filepath}") String filepath){
        super(filepath, "training");
    }

    @PostConstruct
    public void customInit() {
        trainingMap = super.readFromFile();
    }

}
