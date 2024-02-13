package com.gym.gym.services.implementations;

import com.gym.gym.entities.TrainingType;
import com.gym.gym.repositories.TrainingTypeRepository;
import com.gym.gym.services.TrainingTypeHibernateService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TrainingTypeHibernateServiceImpl implements TrainingTypeHibernateService {

    @Autowired
    TrainingTypeRepository trainingTypeRepository;


    @Override
    public TrainingType getTrainingTypeById(long id) {
        return trainingTypeRepository.findById(id).orElse(null);
    }

    @Override
    public List<TrainingType> getAllTrainingTypes() {
        return (List<TrainingType>) trainingTypeRepository.findAll();
    }
}
