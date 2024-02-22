package com.gym.gym.services.implementations;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainerRepository;
import com.gym.gym.services.TrainerService;
import com.gym.gym.services.TrainingTypeService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerServiceImpl implements TrainerService {

    Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    UserServiceImpl userHibernateService;
    @Autowired
    TrainingTypeService trainingTypeService;

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
        User newUser = userHibernateService.createUser(trainer.getUser());
        TrainingType selectedTrainingType = trainingTypeService
                .getTrainingTypeByName(trainer.getSpecialization().getName());

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
    public Trainer updateTrainer(String username, Trainer trainer) {
        //userHibernateService.authenticateUser(trainer.getUser().getUsername(), trainer.getUser().getUsername());

        Trainer existingTrainer = getTrainerByUsername(username);
        TrainingType updatedTrainingType = trainingTypeService
                .getTrainingTypeByName(trainer.getSpecialization().getName());
        User updatedUser = userHibernateService.updateUser(username, trainer.getUser());

        Trainer updatedTrainer = Trainer.builder()
                .id(existingTrainer.getId())
                .user(updatedUser)
                .specialization(updatedTrainingType)
                .build();

        saveTrainer(updatedTrainer);
        logger.info(String.format("Trainee with id %d successfully updated.", updatedTrainer.getId()));
        return updatedTrainer;
    }

    //TODO: Change unit testing
    @Override
    public Boolean changeActiveState(String username, boolean activeState){
        //userHibernateService.authenticateUser(credentials.username, credentials.password);
        return userHibernateService.changeActiveState(username, activeState);
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword){
        return userHibernateService.changePassword(username, oldPassword, newPassword);
    }

}
