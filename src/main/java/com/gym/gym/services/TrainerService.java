package com.gym.gym.services;

import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.entities.Trainer;

import java.util.List;

public interface TrainerService {

    Trainer getTrainerById(long id);

    Trainer getTrainerByUsername(String username);

    List<Trainer> getAllTrainers();

    Trainer createTrainer(TrainerDTO trainerData);

    void saveTrainer(Trainer trainer);

    Trainer updateTrainer(long id, TrainerDTO updatedTrainer);
}
