package com.gym.gym.services;

import com.gym.gym.entities.Trainer;

import java.util.List;

public interface TrainerService {

    Trainer getTrainerById(long id);

    List<Trainer> getAllTrainers();

    Trainer createTrainer(Trainer trainer);

    void saveTrainer(Trainer trainer);

    void updateTrainer(long id, Trainer updatedTrainer);

    void deleteTrainer(long id);
}
