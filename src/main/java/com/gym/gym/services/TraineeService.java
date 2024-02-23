package com.gym.gym.services;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface TraineeService {
    Trainee getTraineeById(long id);

    List<Trainee> getAllTrainees();

    Trainee createTrainee(Trainee trainee);

    @Transactional
    void saveTrainee(Trainee trainee);

    Trainee updateTrainee(String username, Trainee trainee, Credentials credentials);

    List<Trainer> updateTrainerList(String username, List<String> trainerUsernames, Credentials credentials);

    @Transactional
    long deleteTraineeByUsername(String username, Credentials credentials);
}
