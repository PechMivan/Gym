package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TraineeDAOImpl;
import com.gym.gym.entities.Trainee;
import com.gym.gym.services.TraineeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TraineeServiceImpl implements TraineeService {

    @Autowired
    private TraineeDAOImpl traineeDAO;

    @Override
    public Optional<Trainee> getTraineeById(long id) {
        return traineeDAO.get(id);
    }

    @Override
    public List<Trainee> getAllTrainees() {
        return traineeDAO.getAll();
    }

    @Override
    public void saveTrainee(Trainee trainee) {
        traineeDAO.save(trainee);
    }

    @Override
    public void updateTrainee(long id, Trainee updatedTrainee) {
        traineeDAO.update(id, updatedTrainee);
    }

    @Override
    public void deleteTrainee(long id) {
        traineeDAO.delete(id);
    }
}
