package com.gym.gym.services;

import com.gym.gym.entities.Training;

public interface TrainingService {

    Training getTrainingById(long id);

    void createTraining(Training training);
}
