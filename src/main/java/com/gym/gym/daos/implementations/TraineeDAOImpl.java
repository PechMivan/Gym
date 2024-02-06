package com.gym.gym.daos.implementations;

import com.gym.gym.daos.DAO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.storages.DataStorageManager;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public void update(Trainee trainee, String[] params) {

    }

    @Override
    public void delete(long id) {
        Map<Long, Trainee> traineeMap = dataStorageManager.read("trainee");
        traineeMap.remove(id);
        dataStorageManager.write("trainee", traineeMap);

    }
}
