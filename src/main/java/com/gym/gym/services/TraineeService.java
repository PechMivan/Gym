package com.gym.gym.services;

import com.gym.gym.entities.Trainee;

import java.util.List;
import java.util.Optional;

public interface TraineeService {

    Optional<Trainee> getTraineeById(long id);

    List<Trainee> getAllTrainees();

    void saveTrainee(Trainee trainee);

    void updateTrainee(long id, Trainee updatedTrainee);

    void deleteTrainee(long id);
}
