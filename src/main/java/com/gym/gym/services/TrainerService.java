package com.gym.gym.services;

import com.gym.gym.entities.Trainer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

public interface TrainerService {

    Optional<Trainer> getTrainerById(long id);

    List<Trainer> getAllTrainers();

    void saveTrainer(Trainer trainer);

    void updateTrainer(long id, Trainer updatedTrainer);

    void deleteTrainer(long id);
}
