package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TrainerDAOImpl;
import com.gym.gym.entities.Trainer;
import com.gym.gym.services.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TrainerServiceImpl implements TrainerService {

    @Autowired
    private TrainerDAOImpl trainerDAO;

    @Override
    public Optional<Trainer> getTrainerById(long id) {
        return trainerDAO.findById(id);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDAO.findAll();
    }

    @Override
    public void saveTrainer(Trainer trainer) {
        trainerDAO.save(trainer);
    }

    @Override
    public void updateTrainer(long id, Trainer updatedTrainer) {

    }

    @Override
    public void deleteTrainer(long id) {
        trainerDAO.delete(id);
    }
}
