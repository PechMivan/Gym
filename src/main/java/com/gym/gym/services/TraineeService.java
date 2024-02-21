package com.gym.gym.services;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.entities.Trainee;
import jakarta.transaction.Transactional;

import java.util.List;

public interface TraineeService {
    //TODO: Implement Mapper and return a DTO for this method and getAll.
    //TODO: Implement Authorization for the important methods.
    Trainee getTraineeById(long id);

    List<Trainee> getAllTrainees();

    Trainee createTrainee(Trainee trainee);

    @Transactional
    void saveTrainee(Trainee trainee);

    Trainee updateTrainee(long id, Trainee trainee);

    void deleteTrainee(long id, Credentials credentials);

    @Transactional
    long deleteTraineeByUsername(String username, Credentials credentials);

    Boolean toggleTraineeActive(long id, Credentials credentials);

    boolean changePassword(String username, String oldPassword, String newPassword);
}
