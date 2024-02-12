package com.gym.gym.daos;

import com.gym.gym.entities.Training;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface TrainingDAO {

    Optional<Training> findById(long id);

    List<Training> findAll();

    void save(Training training);
}

