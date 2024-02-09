package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TraineeDAOImpl;
import com.gym.gym.entities.Trainee;
import com.gym.gym.services.TraineeService;
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
public class TraineeServiceImpl implements TraineeService {

    Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    Random random = new Random();

    private TraineeDAOImpl traineeDAO;

    @Autowired
    public void setTraineeDAOImpl(TraineeDAOImpl traineeDAO){
        this.traineeDAO = traineeDAO;
    }

    @Override
    public Trainee getTraineeById(long id) {
        return traineeDAO.findById(id)
                .orElseThrow(() -> new IllegalStateException("Trainee doesn't exist with id: " + id));
    }

    @Override
    public List<Trainee> getAllTrainees() {
        return traineeDAO.findAll();
    }

    @Override
    public Trainee createTrainee(Trainee trainee){
        trainee.setUsername(createUsername(trainee.getFirstName(), trainee.getLastName()));
        trainee.setPassword(createPassword());
        saveTrainee(trainee);
        logger.info("User of type Trainee successfully created.");
        return trainee;
    }

    @Override
    public void saveTrainee(Trainee trainee) {
        traineeDAO.save(trainee);
    }

    @Override
    public void updateTrainee(long id, Trainee updatedTrainee) {
        Trainee existingTrainee = getTraineeById(id);

        BeanUtils.copyProperties(updatedTrainee, existingTrainee, getNullPropertyNames(updatedTrainee));

        saveTrainee(existingTrainee);
        logger.info("User of type Trainee successfully updated.");
    }

    @Override
    public void deleteTrainee(long id) {
        traineeDAO.delete(id);
        logger.info("User of type Trainee successfully deleted.");
    }

    public String createUsername(String firstname, String lastname){
        StringBuilder username = new StringBuilder();
        username.append(firstname);
        username.append(".");
        username.append(lastname);

        //Finds all usernames with the same username (ignoring suffix) and counts them.
        long repeatedUsernameSize = getAllTrainees().stream()
                .filter(trainee -> trainee.getUsername().toLowerCase().contains(username.toString().toLowerCase()))
                .count();

        if(repeatedUsernameSize > 0){
            username.append(repeatedUsernameSize);
        }
        logger.info("Username successfully created.");
        return username.toString();
    }

    public String createPassword(){
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;

        //Gets a character from characters Strings based on the random number generated.
        StringBuilder password = new StringBuilder(length);
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
