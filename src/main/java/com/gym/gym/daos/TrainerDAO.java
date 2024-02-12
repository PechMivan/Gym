package com.gym.gym.daos;

import com.gym.gym.entities.Trainer;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface TrainerDAO {

    Optional<Trainer> findById(long id);

    List<Trainer> findAll();

    void save(Trainer trainer);

}

