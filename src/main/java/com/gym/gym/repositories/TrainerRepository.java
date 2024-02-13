package com.gym.gym.repositories;

import com.gym.gym.entities.Trainer;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TrainerRepository extends CrudRepository<Trainer, Long> {
    Optional<Trainer> findByUserUsername(String username);
}
