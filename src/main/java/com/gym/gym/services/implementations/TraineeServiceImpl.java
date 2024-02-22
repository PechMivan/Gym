package com.gym.gym.services.implementations;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TraineeRepository;
import com.gym.gym.services.TraineeService;
import com.gym.gym.services.TrainerService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TraineeServiceImpl implements TraineeService {
    Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private TrainerService trainerService;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public Trainee getTraineeById(long id) {
        return traineeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Trainee with id %d not found", id)));
    }

    public Trainee getTraineeByUsername(String username){
        return traineeRepository.findByUserUsername(username)
                .orElseThrow(() -> new NotFoundException(String.format("Trainee with username %s not found", username)));
    }

    @Override
    public List<Trainee> getAllTrainees() {
        return (List<Trainee>) traineeRepository.findAll();
    }

    @Override
    public Trainee createTrainee(Trainee trainee){
        User newUser = userService.createUser(trainee.getUser());
        Date newDate = trainee.getDateOfBirth();

        Trainee newTrainee = Trainee.builder()
                .user(newUser)
                .dateOfBirth(newDate)
                .address(trainee.getAddress())
                .build();

        saveTrainee(newTrainee);
        logger.info(String.format("Trainee successfully created with id %d", newTrainee.getId()));
        return newTrainee;
    }

    @Override
    @Transactional
    public void saveTrainee(Trainee trainee) {
        traineeRepository.save(trainee);
    }

    @Override
    public Trainee updateTrainee(String username, Trainee trainee) {
        Trainee existingTrainee = getTraineeByUsername(username);

        User updatedUser = userService.updateUser(username, trainee.getUser());
        Date updatedDate = trainee.getDateOfBirth();

        Trainee updatedTrainee = Trainee.builder()
                .id(existingTrainee.getId())
                .user(updatedUser)
                .dateOfBirth(updatedDate)
                .address(trainee.getAddress())
                .build();

        saveTrainee(updatedTrainee);
        logger.info(String.format("Trainee with id %d successfully updated.", updatedTrainee.getId()));
        return updatedTrainee;
    }

    public void updateTrainersList(long id, Trainer trainer){
        Trainee trainee = getTraineeById(id);
        trainee.getTrainers().add(trainer);
        saveTrainee(trainee);
        logger.info(String.format("Trainer with id %d has been added to list of Trainers of Trainee with id %d", trainer.getId(), trainee.getId()));
    }

    @Override
    public void deleteTrainee(long id, Credentials credentials) {
        userService.authenticateUser(credentials.username, credentials.password);
        traineeRepository.deleteById(id);
        logger.info(String.format("Trainee with id %d successfully deleted.", id));
    }

    @Transactional
    @Override
    public long deleteTraineeByUsername(String username, Credentials credentials){
        userService.authenticateUser(credentials.username, credentials.password);
        logger.info(String.format("Trainee with username %s successfully deleted.", username));
        return traineeRepository.deleteByUserUsername(username);
    }

    //TODO: Implement unit testing for this method and add to the service interface
    public List<Trainer> updateTrainerList(String username, List<String> trainerUsernames){
        Trainee trainee = getTraineeByUsername(username);
        //Verify if each trainer exist before saving.

        List<Trainer> trainers = new ArrayList<>();
        Trainer trainer;

        for(String trainerUsername : trainerUsernames){
            trainer = trainerService.getTrainerByUsername(trainerUsername);
            trainers.add(trainer);
        }

        trainee.setTrainers(trainers);
        saveTrainee(trainee);
        return trainee.getTrainers();
    }
}
