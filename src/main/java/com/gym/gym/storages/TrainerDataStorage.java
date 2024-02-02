package com.gym.gym.storages;

import com.gym.gym.entities.Trainer;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

//@Component
@SuppressWarnings("unused")
public class TrainerDataStorage extends DataStorage {

    @Value("$trainer.data.storage.filepath")
    private String filepath;
    private Map<Long, Object> trainerMap;

    @PostConstruct
    public void init() {
        this.trainerMap = super.readFromFile(filepath);
    }
}
