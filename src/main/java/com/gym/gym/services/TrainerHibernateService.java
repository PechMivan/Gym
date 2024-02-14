package com.gym.gym.services;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.entities.Trainer;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TrainerHibernateService {
    Trainer getTrainerById(long id);

    Trainer getTrainerByUsername(String username);

    List<Trainer> getAllTrainers();

    Trainer createTrainer(TrainerDTO trainerData);

    @Transactional
    void saveTrainer(Trainer trainer);

    Trainer updateTrainer(long id, TrainerDTO trainerData);

    Boolean toggleTraineeActive(long id, Credentials credentials);

    boolean changePassword(String username, String oldPassword, String newPassword);
}
