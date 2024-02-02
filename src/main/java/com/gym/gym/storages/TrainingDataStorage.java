package com.gym.gym.storages;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@SuppressWarnings("unused")
public class TrainingDataStorage extends DataStorage {

    @Value("$training.data.storage.filepath")
    private Resource filepath;
    private Map<Long, Object> trainingMap;

    @PostConstruct
    public void init() {
        this.trainingMap = super.readFromFile(filepath);
    }
}