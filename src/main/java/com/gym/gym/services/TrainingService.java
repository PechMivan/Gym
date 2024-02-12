package com.gym.gym.services;

import com.gym.gym.entities.Training;

import java.util.List;

public interface TrainingService {

    Training getTrainingById(long id);

    List<Training> getAllTrainings();

    void createTraining(Training training);
}
