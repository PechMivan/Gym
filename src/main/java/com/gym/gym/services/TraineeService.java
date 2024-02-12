package com.gym.gym.services;

import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.entities.Trainee;

import java.util.List;

public interface TraineeService {

    Trainee getTraineeById(long id);

    List<Trainee> getAllTrainees();

    Trainee createTrainee(TraineeDTO traineeData);

    void saveTrainee(Trainee trainee);

    Trainee updateTrainee(long id, TraineeDTO updatedTrainee);

    void deleteTrainee(long id);
}
