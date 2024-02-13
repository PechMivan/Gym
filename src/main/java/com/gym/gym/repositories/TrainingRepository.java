package com.gym.gym.repositories;

import com.gym.gym.entities.Training;
import org.springframework.data.repository.CrudRepository;

public interface TrainingRepository extends CrudRepository<Training,Long> {
}
