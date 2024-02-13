package com.gym.gym.services.implementations;

import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.User;
import com.gym.gym.repositories.TraineeRepository;
import com.gym.gym.services.TraineeService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class TraineeHibernateServiceImpl implements TraineeService {
    Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    private UserHibernateServiceImpl userHibernateService;

    //TODO: Implement Mapper and return a DTO for this method and getAll.
    //TODO: Implement Authorization for the important methods.
    @Override
    public Trainee getTraineeById(long id) {
        return traineeRepository.findById(id).orElse(new Trainee());
    }

    public Trainee getTraineeByUsername(String username){
        return traineeRepository.findByUserUsername(username).orElse(new Trainee());
    }

    @Override
    public List<Trainee> getAllTrainees() {
        return (List<Trainee>) traineeRepository.findAll();
    }

    @Override
    public Trainee createTrainee(TraineeDTO traineeData){
        User newUser = userHibernateService.createUser(traineeData.userDTO);
        Date newDate = createDate(traineeData.dateOfBirth);

        Trainee newTrainee = Trainee.builder()
                .user(newUser)
                .dateOfBirth(newDate)
                .address(traineeData.address)
                .build();

        saveTrainee(newTrainee);
        logger.info("User of type Trainee successfully created.");
        return newTrainee;
    }

    @Override
    @Transactional
    public void saveTrainee(Trainee trainee) {
        traineeRepository.save(trainee);
    }

    @Override
    public Trainee updateTrainee(long id, TraineeDTO traineeData) {
        Trainee existingTrainee = getTraineeById(id);

        if(existingTrainee == null){
            return null;
        }

        User updatedUser = userHibernateService.updateUser(traineeData.userDTO);
        Date updatedDate = createDate(traineeData.dateOfBirth);

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
        traineeRepository.deleteById(id);
        logger.info("User of type Trainee successfully deleted.");
    }

    @Transactional
    public long deleteTraineeByUsername(String username){
        return traineeRepository.deleteByUserUsername(username);
    }

    public Boolean toggleTraineeActive(long id){
        return userHibernateService.toggleActive(id);
    }

    private Date createDate(String dateOfBirth){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date;
        try {
            date = formatter.parse(dateOfBirth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword){
        return userHibernateService.changePassword(username, oldPassword, newPassword);
    }
}
