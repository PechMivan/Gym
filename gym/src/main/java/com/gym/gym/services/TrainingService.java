package com.gym.gym.services;

import com.gym.gym.dtos.request.TraineeTrainingFindRequest;
import com.gym.gym.dtos.request.TrainerTrainingFindRequest;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;

import java.util.List;

@SuppressWarnings("unused")
public interface TrainingService {

    Training getTrainingById(long id);

    List<Training> getAllTrainings();

    void saveTraining(Training training);

    Training createTraining(Training training);

    List<Trainer> getAllTrainersNotInTraineeTrainersListByUsername(String username);

    List<Training> getFilteredTrainingsForTrainee(TraineeTrainingFindRequest params);

    List<Training> getFilteredTrainingsForTrainer(TrainerTrainingFindRequest params);
}
