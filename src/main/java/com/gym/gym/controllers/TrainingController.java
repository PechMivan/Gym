package com.gym.gym.controllers;

import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.dtos.request.TrainingCreateRequest;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import com.gym.gym.mappers.TrainingMapper;
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
    TrainingServiceImpl trainingService;

    @Autowired
    TrainingMapper trainingMapper;

    @PostMapping
    public ResponseEntity<HttpStatus> createTraining(@RequestBody TrainingCreateRequest request){
        Training training = trainingMapper.mapFromCreateRequest(request);
        Training newTraining = trainingService.createTraining(training);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("{username}")
    public ResponseEntity<List<TrainerDTO>> getAllTrainersNotInTraineeTrainersListByUsername(@PathVariable String username){
        List<Trainer> trainers = trainingService.getAllTrainersNotInTraineeTrainersListByUsername(username);
        List<TrainerDTO> response = trainingMapper.mapTrainerListToTrainerDTOList(trainers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/trainee/username/{username}")
    public ResponseEntity<List<Training>> getTrainingsByTraineeUsernameAndBetweenDates(@PathVariable String username,
                                                                                        @RequestParam String startDate,
                                                                                       @RequestParam String endDate){

        List<Training> trainings = trainingService.getTrainingsByTraineeUsernameAndBetweenDates(username, startDate, endDate);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainee/username2/{username}")
    public ResponseEntity<List<Training>> getTrainingsByTraineeUsernameAndTrainerName(@PathVariable String username,
                                                                                       @RequestParam String trainerName){

        List<Training> trainings = trainingService.getByTraineeUsernameAndTrainerName(username, trainerName);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainee/username3/{username}")
    public ResponseEntity<List<Training>> getTrainingsByTraineeUsernameAndTrainingType(@PathVariable String username,
                                                                                           @RequestParam String trainingType){

        List<Training> trainings = trainingService.getTrainingsByTraineeUsernameAndTrainingType(username, trainingType);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainee/username4/{username}")
    public ResponseEntity<List<Training>> getTrainingsByTrainerUsernameAndBetweenDates(@PathVariable String username,
                                                                                       @RequestParam String startDate,
                                                                                       @RequestParam String endDate){

        List<Training> trainings = trainingService.getTrainingsByTrainerUsernameAndBetweenDates(username, startDate, endDate);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainee/username5/{username}")
    public ResponseEntity<List<Training>> getTrainingsByTrainerUsernameAndTraineeName(@PathVariable String username,
                                                                                      @RequestParam String traineeName){

        List<Training> trainings = trainingService.getTrainingsByTrainerUsernameAndTraineeName(username, traineeName);
        return new ResponseEntity<>(trainings, HttpStatus.OK);
    }

    @GetMapping("/trainee_trainers/{username}")
    public ResponseEntity<List<Trainer>> getTrainingsByTrainerUsernameAndTraineeName(@PathVariable String username){

        List<Trainer> trainers = trainingService.getAllTrainersNotInTraineeTrainersListByUsername(username);
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }

}
