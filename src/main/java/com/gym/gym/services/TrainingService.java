package com.gym.gym.services;

import com.gym.gym.entities.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingService {

    Optional<Training> getTrainingById(long id);

    List<Training> getAllTrainings();

    void saveTraining(Training training);

    void updateTraining(long id, Training updatedTraining);

    void deleteTraining(long id);
}
