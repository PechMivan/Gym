package com.gym.gym.daos.implementations;

import com.gym.gym.daos.DAO;
import com.gym.gym.entities.Trainee;

import java.util.List;
import java.util.Optional;

public class TraineeDAOImpl implements DAO<Trainee> {

    @Override
    public Optional<Trainee> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Trainee> getAll() {
        return null;
    }

    @Override
    public void save(Trainee trainee) {

    }

    @Override
    public void update(Trainee trainee, String[] params) {

    }

    @Override
    public void delete(Trainee trainee) {

    }
}
