package com.gym.gym.storages;

import com.gym.gym.entities.Trainer;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
public class TrainerDataStorage extends DataStorage<Trainer> {

    private Map<Long, Trainer> trainerMap;

    //Actual filepath is retrieved from application.properties.
    public TrainerDataStorage(@Value("${trainerDataStorage.filepath}") String filepath){
        super(filepath, "trainer");
    }

    @PostConstruct
    public void customInit() {
        this.trainerMap = super.readFromFile();
    }

}
