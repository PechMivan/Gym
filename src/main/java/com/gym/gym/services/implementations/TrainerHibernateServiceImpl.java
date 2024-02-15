package com.gym.gym.services.implementations;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainerRepository;
import com.gym.gym.services.TrainerHibernateService;
import com.gym.gym.services.TrainingTypeHibernateService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TrainerHibernateServiceImpl implements TrainerHibernateService {

    Logger logger = LoggerFactory.getLogger(TraineeHibernateServiceImpl.class);
    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    UserHibernateServiceImpl userHibernateService;
    @Autowired
    TrainingTypeHibernateService trainingTypeHibernateService;
    @Autowired
    Validator validator;

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
    public Trainer createTrainer(TrainerDTO trainerData) {
        validateData(trainerData);
        User newUser = userHibernateService.createUser(trainerData.userDTO);
        TrainingType selectedTrainingType = trainingTypeHibernateService.getTrainingTypeByName(trainerData.specialization);

        Trainer newTrainer = Trainer.builder()
                .user(newUser)
                .specialization(selectedTrainingType)
                .build();

        saveTrainer(newTrainer);
        logger.info(String.format("Trainer successfully created with id %d", newTrainer.getId()));
        return newTrainer;
    }

    @Override
    @Transactional
    public void saveTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
    }

    @Override
    public Trainer updateTrainer(long id, TrainerDTO trainerData) {
        userHibernateService.authenticateUser(trainerData.userDTO.username, trainerData.userDTO.password);
        validateData(trainerData);

        Trainer existingTrainer = getTrainerById(id);
        TrainingType updatedTrainingType = trainingTypeHibernateService.getTrainingTypeByName(trainerData.specialization);
        User updatedUser = userHibernateService.updateUser(trainerData.userDTO);

        Trainer updatedTrainer = Trainer.builder()
                .id(existingTrainer.getId())
                .user(updatedUser)
                .specialization(updatedTrainingType)
                .build();

        saveTrainer(updatedTrainer);
        logger.info(String.format("Trainee with id %d successfully updated.", updatedTrainer.getId()));
        return updatedTrainer;
    }

    @Override
    public Boolean toggleTraineeActive(long id, Credentials credentials){
        userHibernateService.authenticateUser(credentials.username, credentials.password);
        return userHibernateService.toggleActive(id);
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword){
        return userHibernateService.changePassword(username, oldPassword, newPassword);
    }

    public void validateData(TrainerDTO trainerData){
        Set<ConstraintViolation<TrainerDTO>> violations = validator.validate(trainerData);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<TrainerDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
    }

}
