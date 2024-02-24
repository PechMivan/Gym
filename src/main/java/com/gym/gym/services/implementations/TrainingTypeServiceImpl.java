package com.gym.gym.services.implementations;

import com.gym.gym.entities.TrainingType;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainingTypeRepository;
import com.gym.gym.services.TrainingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

    @Autowired
    TrainingTypeRepository trainingTypeRepository;

    Logger logger = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);

    @Override
    public TrainingType getTrainingTypeById(long id) {
        return trainingTypeRepository.findById(id).orElse(null);
    }

    @Override
    public TrainingType getTrainingTypeByName(String name){
        return trainingTypeRepository.findByName(name)
                .orElseThrow(() -> new NotFoundException(String.format("Training Type with name %s not found", name)));
    }

    @Override
    public List<TrainingType> getAllTrainingTypes() {
        return (List<TrainingType>) trainingTypeRepository.findAll();
    }
}
