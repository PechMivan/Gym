package com.gym.gym.services.implementations;

import com.gym.gym.dtos.TrainingDTO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.repositories.TrainingRepository;
import com.gym.gym.services.TrainingHibernateService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@SuppressWarnings("unused")
public class TrainingHibernateServiceImpl implements TrainingHibernateService {

    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    TrainingTypeHibernateServiceImpl trainingTypeHibernateService;
    @Autowired
    TrainerHibernateServiceImpl trainerHibernateService;
    @Autowired
    TraineeHibernateServiceImpl traineeHibernateService;

    @Override
    public Training getTrainingById(long id) {
        return trainingRepository.findById(id).orElse(null);
    }

    @Override
    public List<Training> getAllTrainings() {
        return (List<Training>) trainingRepository.findAll();
    }

    @Override
    @Transactional
    public void saveTraining(Training training){
        trainingRepository.save(training);
    }

    @Override
    public Training createTraining(TrainingDTO trainingData) {
        Trainee existingTrainee = traineeHibernateService.getTraineeById(trainingData.traineeId);
        Trainer existingTrainer = trainerHibernateService.getTrainerById(trainingData.trainerId);
        TrainingType existingTrainingType = trainingTypeHibernateService.getTrainingTypeById(trainingData.trainingTypeId);
        Date trainingDate = createDate(trainingData.trainingDate);

        Training newTraining = Training.builder()
                                .trainee(existingTrainee)
                                .trainer(existingTrainer)
                                .trainingType(existingTrainingType)
                                .trainingName(trainingData.trainingName)
                                .trainingDate(trainingDate)
                                .trainingDuration(trainingData.trainingDuration)
                                .build();

        saveTraining(newTraining);
        //log success
        return newTraining;
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
        Date date = null;
        try {
            date = parser.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
