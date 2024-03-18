package com.gym.gym.repositories;

import com.gym.gym.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TrainingRepository extends JpaRepository<Training,Long>, JpaSpecificationExecutor<Training> { }
