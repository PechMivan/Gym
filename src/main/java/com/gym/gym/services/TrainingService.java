package com.gym.gym.services;

import com.gym.gym.entities.Training;

import java.util.List;
import java.util.Optional;

public interface TrainingService {

    Training getTrainingById(long id);

    void createTraining(Training training);
}
