package com.gym.gym.daos.implementations;

import com.gym.gym.daos.DAO;
import com.gym.gym.entities.Trainer;
import com.gym.gym.storages.DataStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TrainerDAOImpl implements DAO<Trainer> {

    @Autowired
    DataStorageManager dataStorageManager;

    @Override
    public Optional<Trainer> get(long id) {
        Map<Long, Trainer> trainerMap = dataStorageManager.read("trainer");
        Trainer trainer = trainerMap.get(id);
        return Optional.ofNullable(trainer);
    }

    @Override
    public List<Trainer> getAll() {
        Map<Long, Trainer> trainerMap = dataStorageManager.read("trainer");
        return trainerMap.values().stream()
                .toList();
    }

    @Override
    public void save(Trainer trainer) {
        Map<Long, Trainer> trainerMap = dataStorageManager.read("trainer");
        trainerMap.put(trainer.getUserId(), trainer);
        dataStorageManager.write("trainer", trainerMap);
    }

    @Override
    public void delete(long id) {
        Map<Long, Trainer> trainerMap = dataStorageManager.read("trainer");
        trainerMap.remove(id);
        dataStorageManager.write("trainer", trainerMap);
    }
}
