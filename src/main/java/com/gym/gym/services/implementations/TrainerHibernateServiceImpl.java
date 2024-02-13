package com.gym.gym.services.implementations;

import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.entities.User;
import com.gym.gym.repositories.TrainerRepository;
import com.gym.gym.services.TrainerService;
import com.gym.gym.services.TrainingTypeHibernateService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerHibernateServiceImpl implements TrainerService {

    Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    @Autowired
    TrainerRepository trainerRepository;
    @Autowired
    UserHibernateServiceImpl userHibernateService;
    @Autowired
    TrainingTypeHibernateService trainingTypeHibernateService;

    @Override
    public Trainer getTrainerById(long id) {
        return trainerRepository.findById(id).orElse(null);
    }

    public Trainer getTrainerByUsername(String username){
        return trainerRepository.findByUserUsername(username).orElse(null);
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return (List<Trainer>) trainerRepository.findAll();
    }

    @Override
    public Trainer createTrainer(TrainerDTO trainerData) {
        User newUser = userHibernateService.createUser(trainerData.userDTO);
        TrainingType selectedTrainingType = trainingTypeHibernateService.getTrainingTypeByName(trainerData.specialization);

        if(selectedTrainingType == null){
            return null;
        }

        Trainer newTrainer = Trainer.builder()
                .user(newUser)
                .specialization(selectedTrainingType)
                .build();

        saveTrainer(newTrainer);
        logger.info("User of type Trainer successfully created.");
        return newTrainer;
    }

    @Override
    @Transactional
    public void saveTrainer(Trainer trainer) {
        trainerRepository.save(trainer);
    }

    @Override
    public Trainer updateTrainer(long id, TrainerDTO trainerData) {
        Trainer existingTrainer = getTrainerById(id);

        if(existingTrainer == null){
            return null;
        }

        TrainingType updatedTrainingType = trainingTypeHibernateService.getTrainingTypeByName(trainerData.specialization);

        if(updatedTrainingType == null){
            return null;
        }

        User updatedUser = userHibernateService.updateUser(trainerData.userDTO);

        if(updatedUser == null){
            return null;
        }

        Trainer updatedTrainer = Trainer.builder()
                .id(existingTrainer.getId())
                .user(updatedUser)
                .specialization(updatedTrainingType)
                .build();

        saveTrainer(updatedTrainer);
        logger.info("User of type Trainee successfully updated.");
        return updatedTrainer;
    }

    public Boolean toggleTraineeActive(long id){
        return userHibernateService.toggleActive(id);
    }

    public boolean changePassword(String username, String oldPassword, String newPassword){
        return userHibernateService.changePassword(username, oldPassword, newPassword);
    }

}
