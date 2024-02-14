package com.gym.gym.services;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.entities.Trainee;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

public interface TraineeHibernateService {
    //TODO: Implement Mapper and return a DTO for this method and getAll.
    //TODO: Implement Authorization for the important methods.
    Trainee getTraineeById(long id);

    List<Trainee> getAllTrainees();

    Trainee createTrainee(TraineeDTO traineeData);

    @Transactional
    void saveTrainee(Trainee trainee);

    Trainee updateTrainee(long id, TraineeDTO traineeData);

    void deleteTrainee(long id, Credentials credentials);

    @Transactional
    long deleteTraineeByUsername(String username, Credentials credentials);

    Boolean toggleTraineeActive(long id, Credentials credentials);

    Date createDate(String dateOfBirth);

    boolean changePassword(String username, String oldPassword, String newPassword);
}
