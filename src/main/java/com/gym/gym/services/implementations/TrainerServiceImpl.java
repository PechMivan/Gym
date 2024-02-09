package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TrainerDAOImpl;
import com.gym.gym.entities.Trainer;
import com.gym.gym.services.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.util.*;

@Component
public class TrainerServiceImpl implements TrainerService {

    Logger logger = LoggerFactory.getLogger(TrainerServiceImpl.class);
    Random random = new Random();
    private TrainerDAOImpl trainerDAO;

    @Autowired
    public void setTrainerDAOImpl(TrainerDAOImpl trainerDAO){
        this.trainerDAO = trainerDAO;
    }

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
    public Trainer createTrainer(Trainer trainer){
        trainer.setUsername(createUsername(trainer.getFirstName(), trainer.getLastName()));
        trainer.setPassword(createPassword());
        saveTrainer(trainer);
        logger.info("User of type Trainer successfully created.");
        return trainer;
    }

    @Override
    public void saveTrainer(Trainer trainer) {
        trainerDAO.save(trainer);
    }

    @Override
    public void updateTrainer(long id, Trainer updatedTrainer) {
        Trainer existingTrainer = getTrainerById(id);

        //Copies properties that are NOT NULL.
        BeanUtils.copyProperties(updatedTrainer, existingTrainer, getNullPropertyNames(updatedTrainer));

        saveTrainer(existingTrainer);
        logger.info("User of type Trainer successfully updated.");
    }

    @Override
    public void deleteTrainer(long id) {
        trainerDAO.delete(id);
        logger.info("User of type Trainee successfully deleted.");
    }

    public String createUsername(String firstname, String lastname){
        StringBuilder username = new StringBuilder();
        username.append(firstname);
        username.append(".");
        username.append(lastname);

        //Finds all usernames with the same username (ignoring suffix) and counts them.
        long repeatedUsernameSize = getAllTrainers().stream()
                .filter(trainee -> trainee.getUsername().toLowerCase().contains(username.toString().toLowerCase()))
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

    /* Finds and returns all fields names with null values from an object */
    private String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
