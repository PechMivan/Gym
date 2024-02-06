package com.gym.gym.daos.implementations;

import com.gym.gym.daos.DAO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.storages.DataStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TraineeDAOImpl implements DAO<Trainee> {

    @Autowired
    DataStorageManager dataStorageManager;

    @Override
    public Optional<Trainee> get(long id) {

        Map<Long, Trainee> traineeMap = dataStorageManager.read("trainee");
        Trainee trainee = traineeMap.get(id);
        return Optional.ofNullable(trainee);
    }

    @Override
    public List<Trainee> getAll() {
        Map<Long, Trainee> traineeMap = dataStorageManager.read("trainee");
        return traineeMap.values().stream()
                .toList();
    }

    @Override
    public void save(Trainee trainee) {
        Map<Long, Trainee> traineeMap = dataStorageManager.read("trainee");
        traineeMap.put(trainee.getUserId(), trainee);
        dataStorageManager.write("trainee", traineeMap);
    }

    @Override
    public void update(long id, Trainee updatedTrainee) {
        Optional<Trainee> existingTraineeOptional = get(id);

        if (existingTraineeOptional.isPresent()) {
            Trainee existingTrainee = existingTraineeOptional.get();

            // Update existing trainee with fields from updatedTrainee
            existingTrainee.setFirstName(updatedTrainee.getFirstName());
            existingTrainee.setLastName(updatedTrainee.getLastName());
            existingTrainee.setUsername(updatedTrainee.getUsername());
            existingTrainee.setPassword(updatedTrainee.getPassword());
            existingTrainee.setActive(updatedTrainee.isActive());
            existingTrainee.setDateOfBirth(updatedTrainee.getDateOfBirth());
            existingTrainee.setAddress(updatedTrainee.getAddress());

            Map<Long, Trainee> traineeMap = dataStorageManager.read("trainee");
            traineeMap.put(id, existingTrainee);
            dataStorageManager.write("trainee", traineeMap);
        } else {
            // Handle case where trainee with given id is not found
            System.out.println("Trainee with id " + id + " not found.");
        }

    }

    @Override
    public void delete(long id) {
        Map<Long, Trainee> traineeMap = dataStorageManager.read("trainee");
        traineeMap.remove(id);
        dataStorageManager.write("trainee", traineeMap);

    }
}
