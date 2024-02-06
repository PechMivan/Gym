package com.gym.gym.daos.implementations;

import com.gym.gym.daos.DAO;
import com.gym.gym.entities.Training;

import java.util.List;
import java.util.Optional;

public class TrainingDAOImpl implements DAO<Training> {

    @Override
    public Optional<Training> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Training> getAll() {
        return null;
    }

    @Override
    public void save(Training training) {

    }

    @Override
    public void update(Training training, String[] params) {

    }

    @Override
    public void delete(Training training) {

    }
}
