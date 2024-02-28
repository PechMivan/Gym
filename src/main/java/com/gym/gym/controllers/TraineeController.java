package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.dtos.request.*;
import com.gym.gym.dtos.response.TraineeFindResponse;
import com.gym.gym.dtos.response.TraineeUpdateResponse;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.mappers.TraineeMapper;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/gym/trainees")
@Validated
@SuppressWarnings("unused")
public class TraineeController {

    @Autowired
    TraineeServiceImpl traineeService;

    @Autowired
    TraineeMapper traineeMapper;

    @GetMapping("/user/{username}")
    public ResponseEntity< TraineeFindResponse > getTraineeByUsername(@PathVariable String username){
        Trainee trainee = traineeService.getTraineeByUsername(username);
        TraineeFindResponse response = traineeMapper.mapToFindResponse(trainee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<Credentials> createTrainee(@RequestBody @Valid TraineeRegistrateRequest request) {
        Trainee trainee = traineeMapper.mapFromRegistrateRequest(request);
        Trainee newTrainee = traineeService.createTrainee(trainee);
        Credentials newCredentials = traineeMapper.mapToCredentials(newTrainee);
        return new ResponseEntity<>(newCredentials, HttpStatus.CREATED); // Status 201
    }

    @PutMapping("/user/{username}")
    public ResponseEntity<TraineeUpdateResponse> updateTrainee(@PathVariable String username, @RequestBody @Valid TraineeUpdateRequest request){
        Trainee trainee = traineeMapper.mapFromUpdateRequest(request);
        Trainee updatedTrainee = traineeService.updateTrainee(username, trainee, request.credentials);
        TraineeUpdateResponse response = traineeMapper.mapToUpdateResponse(updatedTrainee);
        return new ResponseEntity<>(response, HttpStatus.OK); // Status 200
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<String> deleteTraineeByUsername(@PathVariable String username, @RequestBody @Valid Credentials credentials){
        traineeService.deleteTraineeByUsername(username, credentials);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/{username}/trainers")
    public ResponseEntity<List<TrainerDTO>> updateTrainerList(@PathVariable String username, @RequestBody @Valid TraineeTrainersListUpdateRequest request){
        List<Trainer> updatedTrainerList = traineeService.updateTrainerList(username, request.getUsernames(), request.credentials);
        List<TrainerDTO> response = traineeMapper.trainerListToTrainerDTOList(updatedTrainerList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
