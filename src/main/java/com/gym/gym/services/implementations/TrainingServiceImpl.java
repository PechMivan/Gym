package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TrainingDAOImpl;
import com.gym.gym.entities.Training;
import com.gym.gym.services.TrainingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingServiceImpl implements TrainingService {

    Logger logger = LoggerFactory.getLogger(TrainingServiceImpl.class);
    private TrainingDAOImpl trainingDAO;

    @Autowired
    public void setTrainingDAOImpl(TrainingDAOImpl trainingDAO){
        this.trainingDAO = trainingDAO;
    }

    @Override
    public Training getTrainingById(long id) {
        return trainingDAO.findById(id)
                .orElseThrow(() -> new IllegalStateException("Trainer doesn't exist with id: " + id));
    }

    @Override
    public void createTraining(Training training) {
        trainingDAO.save(training);
        logger.info("Training successfully created.");
    }

}
