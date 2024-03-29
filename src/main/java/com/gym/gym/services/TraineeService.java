package com.gym.gym.services;

import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TraineeService {
    Trainee getTraineeById(long id);

    List<Trainee> getAllTrainees();

    Trainee createTrainee(Trainee trainee);

    @Transactional
    void saveTrainee(Trainee trainee);

    Trainee updateTrainee(String username, Trainee trainee);

    void updateTrainersList(long id, Trainer trainer);

    List<Trainer> updateTrainerList(String username, List<String> trainerUsernames);

    @Transactional
    long deleteTraineeByUsername(String username);
}
