package com.gym.gym.repositories;

import com.gym.gym.entities.Trainer;
import org.springframework.data.repository.CrudRepository;

public interface TrainerRepository extends CrudRepository<Long,Trainer> {
}
