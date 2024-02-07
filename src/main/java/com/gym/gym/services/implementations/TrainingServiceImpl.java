package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TrainingDAOImpl;
import com.gym.gym.entities.Training;
import com.gym.gym.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TrainingServiceImpl implements TrainingService {

    @Autowired
    private TrainingDAOImpl trainingDAO;

    @Override
    public Optional<Training> getTrainingById(long id) {
        return trainingDAO.get(id);
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingDAO.getAll();
    }

    @Override
    public void saveTraining(Training training) {
        trainingDAO.save(training);
    }

    @Override
    public void updateTraining(long id, Training updatedTraining) {
        trainingDAO.update(id, updatedTraining);
    }

    @Override
    public void deleteTraining(long id) {
        trainingDAO.delete(id);
    }
}
