package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.ActiveStateChangeRequest;
import com.gym.gym.dtos.request.PasswordChangeRequest;
import com.gym.gym.dtos.request.TraineeRegistrateRequest;
import com.gym.gym.dtos.request.TraineeUpdateRequest;
import com.gym.gym.dtos.response.TraineeFindResponse;
import com.gym.gym.dtos.response.TraineeUpdateResponse;
import com.gym.gym.entities.Trainee;
import com.gym.gym.mappers.TraineeMapper;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/gym/trainees")
@SuppressWarnings("unused")
public class TraineeController {

    @Autowired
    TraineeServiceImpl traineeHibernateService;

    @Autowired
    TraineeMapper traineeMapper;

    @GetMapping("/username/{username}")
    public ResponseEntity< TraineeFindResponse > getTraineeByUsername(@PathVariable String username){
        Trainee trainee = traineeHibernateService.getTraineeByUsername(username);
        TraineeFindResponse response = traineeMapper.mapToFindResponse(trainee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public  ResponseEntity<Credentials> createTrainee(@RequestBody TraineeRegistrateRequest request) {
        Trainee trainee = traineeMapper.mapFromRegistrateRequest(request);
        Trainee newTrainee = traineeHibernateService.createTrainee(trainee);
        Credentials newCredentials = traineeMapper.mapToCredentials(newTrainee);
        return new ResponseEntity<>(newCredentials, HttpStatus.CREATED); // Status 201
    }

    @PostMapping("{username}")
    public ResponseEntity<TraineeUpdateResponse> updateTrainee(@PathVariable String username, @RequestBody TraineeUpdateRequest request){
        Trainee trainee = traineeMapper.mapFromUpdateRequest(request);
        Trainee updatedTrainee = traineeHibernateService.updateTrainee(username, trainee);
        TraineeUpdateResponse response = traineeMapper.mapToUpdateResponse(updatedTrainee);
        return new ResponseEntity<>(response, HttpStatus.OK); // Status 200
    }

    @PostMapping("/username/{username}/delete")
    public ResponseEntity<String> deleteTraineeByUsername(@PathVariable String username, @RequestBody Credentials credentials){
        long count = traineeHibernateService.deleteTraineeByUsername(username, credentials);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO:Check toggleTraineeActive
    @PostMapping("/active/change")
    public ResponseEntity<HttpStatus> changeActiveState(@RequestBody ActiveStateChangeRequest request){
        Boolean activeState = traineeHibernateService.changeActiveState(request.username, request.active);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody PasswordChangeRequest request){
        boolean passwordChanged = traineeHibernateService.changePassword(request.username, request.oldPassword, request.newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
