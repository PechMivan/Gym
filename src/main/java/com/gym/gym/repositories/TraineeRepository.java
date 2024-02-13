package com.gym.gym.repositories;

import com.gym.gym.entities.Trainee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TraineeRepository extends CrudRepository<Trainee,Long> {

}
