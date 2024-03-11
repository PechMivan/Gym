package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.CredentialsAndAccessToken;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.dtos.request.*;
import com.gym.gym.dtos.response.TraineeFindResponse;
import com.gym.gym.dtos.response.TraineeUpdateResponse;
import com.gym.gym.entities.Token;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.mappers.TraineeMapper;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("gym/trainees")
@Validated
@Slf4j
@SuppressWarnings("unused")
public class TraineeController {

    @Autowired
    TraineeServiceImpl traineeService;

    @Autowired
    TraineeMapper traineeMapper;

    @GetMapping(value = "{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity< TraineeFindResponse > getTraineeByUsername(@PathVariable String username){
        Trainee trainee = traineeService.getTraineeByUsername(username);
        TraineeFindResponse response = traineeMapper.mapToFindResponse(trainee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @Timed(value = "create-trainee.time", description = "Time taken to create a trainee")
    public  ResponseEntity<CredentialsAndAccessToken> createTrainee(@RequestBody @Valid TraineeRegistrateRequest request) {
        Trainee trainee = traineeMapper.mapFromRegistrateRequest(request);
        Trainee newTrainee = traineeService.createTrainee(trainee);
        // Gets the one and only token saved at the moment of creation.
        Token accessToken = newTrainee.getUser().getTokens().get(0);
        CredentialsAndAccessToken newCredentials = new CredentialsAndAccessToken
        (
            new Credentials(
                    newTrainee.getUser().getUsername(),
                    newTrainee.getUser().getPassword()
            ),
            accessToken.getToken()
        );
        return new ResponseEntity<>(newCredentials, HttpStatus.CREATED); // Status 201
    }

    @PutMapping("{username}")
    public ResponseEntity<TraineeUpdateResponse> updateTrainee(@PathVariable String username, @RequestBody @Valid TraineeUpdateRequest request){
        Trainee trainee = traineeMapper.mapFromUpdateRequest(request);
        Trainee updatedTrainee = traineeService.updateTrainee(username, trainee);
        TraineeUpdateResponse response = traineeMapper.mapToUpdateResponse(updatedTrainee);
        return new ResponseEntity<>(response, HttpStatus.OK); // Status 200
    }

    @DeleteMapping("{username}")
    public ResponseEntity<String> deleteTraineeByUsername(@PathVariable String username){
        traineeService.deleteTraineeByUsername(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{username}/trainers")
    public ResponseEntity<List<TrainerDTO>> updateTrainerList(@PathVariable String username, @RequestBody @Valid TraineeTrainersListUpdateRequest request){
        List<Trainer> updatedTrainerList = traineeService.updateTrainerList(username, request.getUsernames());
        List<TrainerDTO> response = traineeMapper.trainerListToTrainerDTOList(updatedTrainerList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
