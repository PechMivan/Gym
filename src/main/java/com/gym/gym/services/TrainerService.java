package com.gym.gym.services;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.entities.Trainer;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TrainerService {
    Trainer getTrainerById(long id);

    Trainer getTrainerByUsername(String username);

    List<Trainer> getAllTrainers();

    Trainer createTrainer(Trainer trainer);

    @Transactional
    void saveTrainer(Trainer trainer);

    Trainer updateTrainer(String username, Trainer trainer);

    Boolean toggleTraineeActive(long id, Credentials credentials);

    boolean changePassword(String username, String oldPassword, String newPassword);
}
