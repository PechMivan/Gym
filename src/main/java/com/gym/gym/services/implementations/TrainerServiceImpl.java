package com.gym.gym.services.implementations;

import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.customExceptions.NotFoundException;
import com.gym.gym.repositories.TrainerRepository;
import com.gym.gym.services.TrainerService;
import com.gym.gym.services.TrainingTypeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;
    private final UserServiceImpl userService;
    private final TrainingTypeService trainingTypeService;

    @Override
    public Trainer getTrainerById(long id) {
        return trainerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Trainer with id %d not found", id)));
    }

    @Override
    public Trainer getTrainerByUsername(String username){
        return trainerRepository.findByUserUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("Trainer with username %s not found", username)));
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return (List<Trainer>) trainerRepository.findAll();
    }

    @Override
    public Trainer createTrainer(Trainer trainer) {
        TrainingType selectedTrainingType = trainingTypeService
                .getTrainingTypeByName(trainer.getSpecialization().getName());
        User newUser = userService.createUser(trainer.getUser());

        Trainer newTrainer = Trainer.builder()
                .user(newUser)
                .specialization(selectedTrainingType)
                .build();

        saveTrainer(newTrainer);
        log.info(String.format("Trainer successfully created with id %d", newTrainer.getId()));
        return newTrainer;
    }

    @Override
    @Transactional
    public void saveTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
    }

    @Override
    public Trainer updateTrainer(String username, Trainer trainer) {
        Trainer existingTrainer = getTrainerByUsername(username);
        TrainingType updatedTrainingType = trainingTypeService
                .getTrainingTypeByName(trainer.getSpecialization().getName());
        User updatedUser = userService.updateUser(username, trainer.getUser());

        Trainer updatedTrainer = Trainer.builder()
                .id(existingTrainer.getId())
                .user(updatedUser)
                .specialization(updatedTrainingType)
                .build();

        saveTrainer(updatedTrainer);
        log.info(String.format("Trainer with id %d successfully updated.", updatedTrainer.getId()));
        return updatedTrainer;
    }

}
