package com.gym.gym.services.implementations;

import com.gym.gym.entities.TrainingType;
import com.gym.gym.repositories.TrainingTypeRepository;
import com.gym.gym.services.TrainingTypeHibernateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeHibernateServiceImpl implements TrainingTypeHibernateService {

    @Autowired
    TrainingTypeRepository trainingTypeRepository;

    Logger logger = LoggerFactory.getLogger(TrainingTypeHibernateServiceImpl.class);

    @Override
    public TrainingType getTrainingTypeById(long id) {
        return trainingTypeRepository.findById(id).orElse(null);
    }

    @Override
    public TrainingType getTrainingTypeByName(String name){
        return trainingTypeRepository.findByTrainingTypeName(name).orElse(null);
    }

    @Override
    public List<TrainingType> getAllTrainingTypes() {
        return (List<TrainingType>) trainingTypeRepository.findAll();
    }
}
