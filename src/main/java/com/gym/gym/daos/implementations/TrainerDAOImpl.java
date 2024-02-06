package com.gym.gym.daos.implementations;

import com.gym.gym.daos.DAO;
import com.gym.gym.entities.Trainer;

import java.util.List;
import java.util.Optional;

public class TrainerDAOImpl implements DAO<Trainer> {

    @Override
    public Optional<Trainer> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Trainer> getAll() {
        return null;
    }

    @Override
    public void save(Trainer trainer) {

    }

    @Override
    public void update(Trainer trainer, String[] params) {

    }

    @Override
    public void delete(long id) {

    }
}
