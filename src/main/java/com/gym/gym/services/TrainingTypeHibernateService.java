package com.gym.gym.services;

import com.gym.gym.entities.TrainingType;

import java.util.List;

public interface TrainingTypeHibernateService {

    TrainingType getTrainingTypeById(long id);
    List<TrainingType> getAllTrainingTypes();
}
