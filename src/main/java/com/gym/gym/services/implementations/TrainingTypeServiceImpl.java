package com.gym.gym.services.implementations;

import com.gym.gym.entities.TrainingType;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainingTypeRepository;
import com.gym.gym.services.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;

    @Override
    public TrainingType getTrainingTypeById(long id) {
        return trainingTypeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Training Type with name %d not found", id)));
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
