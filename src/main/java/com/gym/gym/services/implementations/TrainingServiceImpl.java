package com.gym.gym.services.implementations;

import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainingRepository;
import com.gym.gym.services.TrainingService;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    TrainingTypeServiceImpl trainingTypeHibernateService;
    @Autowired
    TrainerServiceImpl trainerHibernateService;
    @Autowired
    TraineeServiceImpl traineeHibernateService;
    @Autowired
    Validator validator;

    Logger logger = LoggerFactory.getLogger(TraineeServiceImpl.class);

    @Override
    public Training getTrainingById(long id) {
        return trainingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Training with id %d not found", id)));
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    @Transactional
    public void saveTraining(Training training){
        trainingRepository.save(training);
    }

    @Override
    public Training createTraining(Training training) {
        //validateData(training);
        Trainee existingTrainee = traineeHibernateService.getTraineeByUsername(training.getTrainee().getUser().getUsername());
        Trainer existingTrainer = trainerHibernateService.getTrainerByUsername(training.getTrainer().getUser().getUsername());
        TrainingType trainingType = existingTrainer.getSpecialization();

        Training newTraining = Training.builder()
                                .trainee(existingTrainee)
                                .trainer(existingTrainer)
                                .trainingType(trainingType)
                                .name(training.getName())
                                .date(training.getDate())
                                .duration(training.getDuration())
                                .build();

        saveTraining(newTraining);
        traineeHibernateService.updateTrainersList(existingTrainee.getId(), existingTrainer);
        logger.info(String.format("Training successfully created with id %d ", newTraining.getId()));
        return newTraining;
    }

    public List<Trainer> getAllTrainersNotInTraineeTrainersListByUsername(String username){
        Trainee trainee = traineeHibernateService.getTraineeByUsername(username);
        List<Trainer> trainers = trainerHibernateService.getAllTrainers();
        List<Trainer> trainersOfTrainee = trainee.getTrainers();

        trainers = trainers.stream()
                .filter(trainer -> !trainersOfTrainee.contains(trainer) && trainer.getUser().isActive())
                .collect(Collectors.toList());

        return trainers;
    }

    @Override
    public List<Training> getTrainingsByTraineeUsernameAndBetweenDates(String username, String startDate, String endDate){
        Date start = createDate(startDate);
        Date end = createDate(endDate);

        return trainingRepository.findAllTrainingsByTraineeUsernameAndBetweenDates(username, start, end);
    }

    @Override
    public List<Training> getTrainingsByTrainerUsernameAndBetweenDates(String username, String startDate, String endDate){
        Date start = createDate(startDate);
        Date end = createDate(endDate);

        return trainingRepository.findAllTrainingsByTrainerUsernameAndBetweenDates(username, start, end);
    }

    @Override
    public List<Training> getByTraineeUsernameAndTrainerName(String username, String trainerName){
        return trainingRepository.findAllTrainingsByTraineeUsernameAndTrainerName(username, trainerName);
    }

    @Override
    public List<Training> getTrainingsByTrainerUsernameAndTraineeName(String username, String traineeName){
        return trainingRepository.findAllTrainingsByTrainerUsernameAndTraineeName(username, traineeName);
    }

    @Override
    public List<Training> getTrainingsByTraineeUsernameAndTrainingType(String username, String trainingType){
        return trainingRepository.findAllTrainingsByTraineeUsernameAndTrainingType(username, trainingType);
    }

    private Date createDate(String dateString){
        DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = parser.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public void validateData(Training trainingData){
        Set<ConstraintViolation<Training>> violations = validator.validate(trainingData);

        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Training> constraintViolation : violations) {
                sb.append(constraintViolation.getMessage());
            }

            throw new ConstraintViolationException("Error occurred: " + sb, violations);
        }
    }
}
