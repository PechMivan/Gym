package com.gym.gym.services.implementations;

import com.gym.gym.daos.implementations.TraineeDAOImpl;
import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.User;
import com.gym.gym.services.TraineeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TraineeServiceImpl implements TraineeService {
    long idCounter = 0;
    Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);
    Random random = new Random();

    private TraineeDAOImpl traineeDAO;

    @Autowired
    public void setTraineeDAOImpl(TraineeDAOImpl traineeDAO){
        this.traineeDAO = traineeDAO;
    }

    //TODO: Implement Mapper and return a DTO for this method and getAll.
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
    public Trainee createTrainee(TraineeDTO traineeData){

        String newUsername = createUsername(traineeData.userDTO.firstname, traineeData.userDTO.lastname);
        String newPassword = createPassword();

        User newUser = User.builder()
                .firstName(traineeData.userDTO.firstname)
                .lastName(traineeData.userDTO.lastname)
                .username(newUsername)
                .password(newPassword)
                .isActive(traineeData.userDTO.isActive)
                .build();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        Date newDate;
        try {
            newDate = formatter.parse(traineeData.dateOfBirth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Trainee newTrainee = Trainee.builder()
                .id(++idCounter)
                .user(newUser)
                .dateOfBirth(newDate)
                .address(traineeData.address)
                .build();

        saveTrainee(newTrainee);
        logger.info("User of type Trainee successfully created.");
        return newTrainee;
    }

    @Override
    public void saveTrainee(Trainee trainee) {
        traineeDAO.save(trainee);
    }

    @Override
    public Trainee updateTrainee(long id, TraineeDTO traineeData) {
        Trainee existingTrainee = getTraineeById(id);

        User updatedUser = User.builder()
                .firstName(traineeData.userDTO.firstname)
                .lastName(traineeData.userDTO.lastname)
                .username(existingTrainee.getUser().getUsername())
                .password(existingTrainee.getUser().getPassword())
                .isActive(traineeData.userDTO.isActive)
                .build();

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);

        Date updatedDate;
        try {
            updatedDate = formatter.parse(traineeData.dateOfBirth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        Trainee updatedTrainee = Trainee.builder()
                .id(existingTrainee.getId())
                .user(updatedUser)
                .dateOfBirth(updatedDate)
                .address(traineeData.address)
                .build();

        saveTrainee(updatedTrainee);
        logger.info("User of type Trainee successfully updated.");
        return updatedTrainee;
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
                .filter(trainee -> trainee.getUser().getUsername().toLowerCase().contains(username.toString().toLowerCase()))
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
}
