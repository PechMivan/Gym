package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TrainingDAOImpl;
import com.gym.gym.entities.Training;
import com.gym.gym.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingDAOImpl trainingDAO;

    @Override
    public Optional<Training> getTrainingById(long id) {
        return trainingDAO.get(id);
    }

    @Override
    public void createTraining(Training training) {
        trainingDAO.save(training);
    }

}
