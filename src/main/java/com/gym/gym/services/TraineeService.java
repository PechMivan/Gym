package com.gym.gym.services;

import com.gym.gym.entities.Trainee;

import java.util.List;

public interface TraineeService {

    Trainee getTraineeById(long id);

    List<Trainee> getAllTrainees();

    Trainee createTrainee(Trainee trainee);

    void saveTrainee(Trainee trainee);

    void updateTrainee(long id, Trainee updatedTrainee);

    void deleteTrainee(long id);
}
