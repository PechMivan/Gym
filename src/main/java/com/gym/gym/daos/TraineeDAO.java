package com.gym.gym.daos;

import com.gym.gym.entities.Trainee;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unused")
public interface TraineeDAO {

    Optional<Trainee> findById(long id);

    List<Trainee> findAll();

    void save(Trainee trainee);

    void delete(long id);
}
