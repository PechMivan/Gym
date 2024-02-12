package com.gym.gym.services.implementations;

import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.services.TraineeService;

import java.util.List;

public class TraineeHibernateServiceImpl implements TraineeService {
    @Override
    public Trainee getTraineeById(long id) {
        return null;
    }

    @Override
    public List<Trainee> getAllTrainees() {
        return null;
    }

    @Override
    public Trainee createTrainee(TraineeDTO traineeData) {
        return null;
    }

    @Override
    public void saveTrainee(Trainee trainee) {

    }

    @Override
    public Trainee updateTrainee(long id, TraineeDTO updatedTrainee) {
        return null;
    }

    @Override
    public void deleteTrainee(long id) {

    }
}
