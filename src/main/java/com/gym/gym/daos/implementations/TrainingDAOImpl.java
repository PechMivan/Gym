package com.gym.gym.daos.implementations;

import com.gym.gym.daos.DAO;
import com.gym.gym.entities.Training;
import com.gym.gym.storages.DataStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TrainingDAOImpl implements DAO<Training> {

    DataStorageManager dataStorageManager;

    @Autowired
    public void setDataStorageManager(DataStorageManager dataStorageManager){
        this.dataStorageManager = dataStorageManager;
    }

    @Override
    public Optional<Training> findById(long id) {
        Map<Long, Training> trainingMap = dataStorageManager.read("training");
        Training training = trainingMap.get(id);
        return Optional.ofNullable(training);
    }

    @Override
    public List<Training> findAll() {
        Map<Long, Training> trainingMap = dataStorageManager.read("training");
        return trainingMap.values().stream()
                .toList();
    }

    @Override
    public void save(Training training) {
        Map<Long, Training> trainingMap = dataStorageManager.read("training");
        trainingMap.put(training.getId(), training);
        dataStorageManager.write("training", trainingMap);
    }

    @Override
    public void delete(long id) {
        Map<Long, Training> trainingMap = dataStorageManager.read("training");
        trainingMap.remove(id);
        dataStorageManager.write("training", trainingMap);
    }
}

