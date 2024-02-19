package com.gym.gym.controllers;

import com.gym.gym.dtos.TrainingDTO;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import com.gym.gym.services.implementations.TrainingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gym/trainings")
@SuppressWarnings("unused")
public class TrainingController {

    @Autowired
    TrainingServiceImpl trainingHibernateService;

    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody TrainingDTO data){
        Training training = trainingHibernateService.createTraining(data);
        if (training != null){
            return new ResponseEntity<>(training, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/trainee/username/{username}")
    public ResponseEntity<List<Training>> getTrainingsByTraineeUsernameAndBetweenDates(@PathVariable String username,
                                                                                        @RequestParam String startDate,
                                                                                       @RequestParam String endDate){

        List<Training> trainings = trainingHibernateService.getTrainingsByTraineeUsernameAndBetweenDates(username, startDate, endDate);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainee/username2/{username}")
    public ResponseEntity<List<Training>> getTrainingsByTraineeUsernameAndTrainerName(@PathVariable String username,
                                                                                       @RequestParam String trainerName){

        List<Training> trainings = trainingHibernateService.getByTraineeUsernameAndTrainerName(username, trainerName);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainee/username3/{username}")
    public ResponseEntity<List<Training>> getTrainingsByTraineeUsernameAndTrainingType(@PathVariable String username,
                                                                                           @RequestParam String trainingType){

        List<Training> trainings = trainingHibernateService.getTrainingsByTraineeUsernameAndTrainingType(username, trainingType);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainee/username4/{username}")
    public ResponseEntity<List<Training>> getTrainingsByTrainerUsernameAndBetweenDates(@PathVariable String username,
                                                                                       @RequestParam String startDate,
                                                                                       @RequestParam String endDate){

        List<Training> trainings = trainingHibernateService.getTrainingsByTrainerUsernameAndBetweenDates(username, startDate, endDate);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainee/username5/{username}")
    public ResponseEntity<List<Training>> getTrainingsByTrainerUsernameAndTraineeName(@PathVariable String username,
                                                                                      @RequestParam String traineeName){

        List<Training> trainings = trainingHibernateService.getTrainingsByTrainerUsernameAndTraineeName(username, traineeName);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainee_trainers/{username}")
    public ResponseEntity<List<Trainer>> getTrainingsByTrainerUsernameAndTraineeName(@PathVariable String username){

        List<Trainer> trainers = trainingHibernateService.getAllTrainersNotInTraineeTrainersListByUsername(username);
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }

}
