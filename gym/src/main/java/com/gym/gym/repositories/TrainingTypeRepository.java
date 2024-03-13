package com.gym.gym.repositories;

import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.TrainingType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrainingTypeRepository extends CrudRepository<TrainingType,Long> {

    Optional<TrainingType> findByName(String name);
}
