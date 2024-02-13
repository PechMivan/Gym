package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TrainerDAOImpl;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.entities.User;
import com.gym.gym.services.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@SuppressWarnings("unused")
public class TrainerServiceImpl implements TrainerService {

    long idCounter = 0;
    Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    Random random = new Random();
    private TrainerDAOImpl trainerDAO;

    @Autowired
    public void setTrainerDAOImpl(TrainerDAOImpl trainerDAO){
        this.trainerDAO = trainerDAO;
    }

    //TODO: Implement Mapper and return a DTO for this method and getAll.
    @Override
    public Trainer getTrainerById(long id) {
        return trainerDAO.findById(id)
                .orElseThrow(() -> new IllegalStateException("Trainer doesn't exist with id: " + id));
    }

    @Override
    public List<Trainer> getAllTrainers() {
        return trainerDAO.findAll();
    }

    @Override
    public Trainer createTrainer(TrainerDTO trainerData){
        String newUsername = createUsername(trainerData.userDTO.firstname, trainerData.userDTO.lastname);
        String newPassword = createPassword();

        User newUser = User.builder()
                           .firstName(trainerData.userDTO.firstname)
                           .lastName(trainerData.userDTO.lastname)
                           .username(newUsername)
                           .password(newPassword)
                           .isActive(trainerData.userDTO.isActive)
                           .build();

        TrainingType newTrainingType = TrainingType.builder()
                                                   .trainingTypeName(trainerData.specialization)
                                                   .build();

        Trainer newTrainer = Trainer.builder()
                                    .id(++idCounter)
                                    .user(newUser)
                                    .specialization(newTrainingType)
                                    .build();

        saveTrainer(newTrainer);
        logger.info("User of type Trainer successfully created.");
        return newTrainer;
    }

    @Override
    public void saveTrainer(Trainer trainer) {
        trainerDAO.save(trainer);
    }

    @Override
    public Trainer updateTrainer(long id, TrainerDTO trainerData) {
        Trainer existingTrainer = getTrainerById(id);

        User updatedUser = User.builder()
                .firstName(trainerData.userDTO.firstname)
                .lastName(trainerData.userDTO.lastname)
                .username(existingTrainer.getUser().getUsername())
                .password(existingTrainer.getUser().getPassword())
                .isActive(trainerData.userDTO.isActive)
                .build();

        TrainingType updatedTrainingType = TrainingType.builder()
                .trainingTypeName(trainerData.specialization)
                .build();

        Trainer updatedTrainer = Trainer.builder()
                .id(existingTrainer.getId())
                .user(updatedUser)
                .specialization(updatedTrainingType)
                .build();

        saveTrainer(updatedTrainer);
        logger.info("User of type Trainer successfully updated.");
        return updatedTrainer;
    }

    public String createUsername(String firstname, String lastname){
        StringBuilder username = new StringBuilder();
        username.append(firstname);
        username.append(".");
        username.append(lastname);

        //Finds all usernames with the same username (ignoring suffix) and counts them.
        long repeatedUsernameSize = getAllTrainers().stream()
                .filter(trainee -> trainee.getUser().getUsername().toLowerCase().contains(username.toString().toLowerCase()))
                .count();

        if(repeatedUsernameSize > 0){
            logger.info("There are " + repeatedUsernameSize + " users with the same username.");
            username.append(repeatedUsernameSize);
        }
        logger.info("Username successfully created.");
        return username.toString();
    }

    //
    public String createPassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;

        StringBuilder password = new StringBuilder(length);
        //Gets a character from characters Strings based on the random number generated.
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        logger.info("Password successfully created.");
        return password.toString();
    }

}
