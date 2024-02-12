package com.gym.gym.daos.implementations;

import com.gym.gym.daos.TrainerDAO;
import com.gym.gym.entities.Trainer;
import com.gym.gym.storages.DataStorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TrainerDAOImpl implements TrainerDAO {

    DataStorageManager dataStorageManager;

    @Autowired
    public void setDataStorageManager(DataStorageManager dataStorageManager){
        this.dataStorageManager = dataStorageManager;
    }

    @Override
    public Optional<Trainer> findById(long id) {
        Map<Long, Trainer> trainerMap = dataStorageManager.read("trainer");
        Trainer trainer = trainerMap.get(id);
        return Optional.ofNullable(trainer);
    }

    @Override
    public List<Trainer> findAll() {
        Map<Long, Trainer> trainerMap = dataStorageManager.read("trainer");
        return trainerMap.values().stream()
                .toList();
    }

    @Override
    public void save(Trainer trainer) {
        Map<Long, Trainer> trainerMap = dataStorageManager.read("trainer");
        trainerMap.put(trainer.getId(), trainer);
        dataStorageManager.write("trainer", trainerMap);
    }
}
