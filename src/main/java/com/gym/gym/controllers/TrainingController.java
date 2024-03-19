package com.gym.gym.controllers;

import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.dtos.request.TraineeTrainingFindRequest;
import com.gym.gym.dtos.request.TrainerTrainingFindRequest;
import com.gym.gym.dtos.request.TrainingCreateRequest;
import com.gym.gym.dtos.response.TraineeTrainingFindResponse;
import com.gym.gym.dtos.response.TrainerTrainingFindResponse;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import com.gym.gym.mappers.TrainingMapper;
import com.gym.gym.repositories.TrainingRepository;
import com.gym.gym.services.implementations.TrainingServiceImpl;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("gym/trainings")
@RequiredArgsConstructor
@Validated
@SuppressWarnings("unused")
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    @PostMapping
    @Timed(value = "create-training.time", description = "Time taken to create a training")
    public ResponseEntity<HttpStatus> createTraining(@RequestBody @Valid TrainingCreateRequest request){
        Training training = trainingMapper.mapFromCreateRequest(request);
        Training newTraining = trainingService.createTraining(training);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/trainee/{username}")
    public ResponseEntity<List<TrainerDTO>> getAllTrainersNotInTraineeTrainersListByUsername(@PathVariable String username){
        List<Trainer> trainers = trainingService.getAllTrainersNotInTraineeTrainersListByUsername(username);
        List<TrainerDTO> response = trainingMapper.mapTrainerListToTrainerDTOList(trainers);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/trainee/filter")
    public ResponseEntity< List<TraineeTrainingFindResponse> > getFilteredTrainingsForTrainee(@RequestBody @Valid TraineeTrainingFindRequest request){
        List<Training> trainings = trainingService.getFilteredTrainingsForTrainee(request);
        List<TraineeTrainingFindResponse> responses = trainingMapper.mapToFindTraineeTrainingResponseList(trainings);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/trainer/filter")
    public ResponseEntity< List<TrainerTrainingFindResponse> > getFilteredTrainingsForTrainer(@RequestBody @Valid TrainerTrainingFindRequest request){
        List<Training> trainings = trainingService.getFilteredTrainingsForTrainer(request);
        List<TrainerTrainingFindResponse> responses = trainingMapper.mapToFindTrainerTrainingResponseList(trainings);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

}
