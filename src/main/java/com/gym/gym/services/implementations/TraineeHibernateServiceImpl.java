package com.gym.gym.services.implementations;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.User;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TraineeRepository;
import com.gym.gym.services.TraineeHibernateService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
public class TraineeHibernateServiceImpl implements TraineeHibernateService {
    Logger logger = LoggerFactory.getLogger(TraineeHibernateServiceImpl.class);

    @Autowired
    private TraineeRepository traineeRepository;
    @Autowired
    Validator validator;
    @Autowired
    private UserHibernateServiceImpl userHibernateService;

    //TODO: Implement Mapper and return a DTO for this method and getAll.
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
    public Trainee createTrainee(TraineeDTO traineeData){
        validateData(traineeData);
        User newUser = userHibernateService.createUser(traineeData.userDTO);
        Date newDate = createDate(traineeData.dateOfBirth);

        Trainee newTrainee = Trainee.builder()
                .user(newUser)
                .dateOfBirth(newDate)
                .address(traineeData.address)
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
    public Trainee updateTrainee(long id, TraineeDTO traineeData) {
        userHibernateService.authenticateUser(traineeData.userDTO.username, traineeData.userDTO.password);
        Trainee existingTrainee = getTraineeById(id);

        User updatedUser = userHibernateService.updateUser(traineeData.userDTO);
        Date updatedDate = createDate(traineeData.dateOfBirth);

        Trainee updatedTrainee = Trainee.builder()
                .id(existingTrainee.getId())
                .user(updatedUser)
                .dateOfBirth(updatedDate)
                .address(traineeData.address)
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
        userHibernateService.authenticateUser(credentials.username, credentials.password);
        traineeRepository.deleteById(id);
        logger.info(String.format("Trainee with id %d successfully deleted.", id));
    }

    @Transactional
    @Override
    public long deleteTraineeByUsername(String username, Credentials credentials){
        userHibernateService.authenticateUser(credentials.username, credentials.password);
        logger.info(String.format("Trainee with username %s successfully deleted.", username));
        return traineeRepository.deleteByUserUsername(username);
    }

    @Override
    public Boolean toggleTraineeActive(long id, Credentials credentials){
        userHibernateService.authenticateUser(credentials.username, credentials.password);
        return userHibernateService.toggleActive(id);
    }

    @Override
    public Date createDate(String dateOfBirth){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date;
        try {
            date = formatter.parse(dateOfBirth);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword){
        return userHibernateService.changePassword(username, oldPassword, newPassword);
    }

    public void validateData(TraineeDTO traineeData){
        Set<ConstraintViolation<TraineeDTO>> violations = validator.validate(traineeData);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<TraineeDTO> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ConstraintViolationException("Error occurred: " + sb.toString(), violations);
        }
    }
}
