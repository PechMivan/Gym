package com.gym.gym.services.implementations;

import com.gym.gym.entities.Training;
import com.gym.gym.repositories.TrainingRepository;
import com.gym.gym.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TrainingHibernateServiceImpl implements TrainingService {

    @Autowired
    TrainingRepository trainingRepository;

    @Override
    public Training getTrainingById(long id) {
        return null;
    }

    @Override
    public List<Training> getAllTrainings() {
        return null;
    }

    @Override
    public void createTraining(Training training) {

    }
}
