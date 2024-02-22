package com.gym.gym.services;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.entities.Trainee;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TraineeService {
    Trainee getTraineeById(long id);

    List<Trainee> getAllTrainees();

    Trainee createTrainee(Trainee trainee);

    @Transactional
    void saveTrainee(Trainee trainee);

    Trainee updateTrainee(String username, Trainee trainee);

    void deleteTrainee(long id, Credentials credentials);

    @Transactional
    long deleteTraineeByUsername(String username, Credentials credentials);

    Boolean changeActiveState(String username, boolean activeState);

    boolean changePassword(String username, String oldPassword, String newPassword);
}
