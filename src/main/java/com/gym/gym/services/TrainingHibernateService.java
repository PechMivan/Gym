package com.gym.gym.services;

import com.gym.gym.dtos.TrainingDTO;
import com.gym.gym.entities.Training;

import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
public interface TrainingHibernateService {

    Training getTrainingById(long id);

    List<Training> getAllTrainings();

    void saveTraining(Training training);

    Training createTraining(TrainingDTO training);

    List<Training> getTrainingsByUsernameAndBetweenDates(String username, String startDate, String endDate);
}
