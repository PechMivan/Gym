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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    public List<Training> getTrainingsByUsernameAndBetweenDates(String username, String startDate, String endDate){
        return trainingRepository.findTrainingsByUsernameAndBetweenDates(username, startDate, endDate);
    }

    private Date createDate(String dateString){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date date;
        try {
            date = formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }
}
