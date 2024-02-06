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

    @Autowired
    DataStorageManager dataStorageManager;

    @Override
    public Optional<Training> get(long id) {
        Map<Long, Training> trainingMap = dataStorageManager.read("training");
        Training training = trainingMap.get(id);
        return Optional.ofNullable(training);
    }

    @Override
    public List<Training> getAll() {
        Map<Long, Training> trainingMap = dataStorageManager.read("training");
        return trainingMap.values().stream()
                .toList();
    }

    @Override
    public void save(Training training) {
        Map<Long, Training> trainingMap = dataStorageManager.read("training");
        trainingMap.put(training.getTrainingId(), training);
        dataStorageManager.write("training", trainingMap);
    }

    @Override
    public void update(long id, Training updatedTraining) {
        Optional<Training> existingTrainingOptional = get(id);

        if (existingTrainingOptional.isPresent()) {
            Training existingTraining = existingTrainingOptional.get();

            // Update existing training with fields from updatedTraining
            existingTraining.setTraineeId(updatedTraining.getTraineeId());
            existingTraining.setTrainerId(updatedTraining.getTrainerId());
            existingTraining.setTrainingName(updatedTraining.getTrainingName());
            existingTraining.setTrainingType(updatedTraining.getTrainingType());
            existingTraining.setTrainingDate(updatedTraining.getTrainingDate());
            existingTraining.setTrainingDuration(updatedTraining.getTrainingDuration());

            Map<Long, Training> trainingMap = dataStorageManager.read("training");
            trainingMap.put(id, existingTraining);
            dataStorageManager.write("training", trainingMap);
        } else {
            // Handle case where training with given id is not found
            System.out.println("Training with id " + id + " not found.");
        }
    }

    @Override
    public void delete(long id) {
        Map<Long, Training> trainingMap = dataStorageManager.read("training");
        trainingMap.remove(id);
        dataStorageManager.write("training", trainingMap);
    }
}

