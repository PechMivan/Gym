package com.gym.gym.services;

import com.gym.gym.entities.Training;

import java.util.List;

@SuppressWarnings("unused")
public interface TrainingService {

    Training getTrainingById(long id);

    List<Training> getAllTrainings();

    void saveTraining(Training training);

    Training createTraining(Training training);
}
