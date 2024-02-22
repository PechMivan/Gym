package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.ActiveStateChangeRequest;
import com.gym.gym.dtos.request.PasswordChangeRequest;
import com.gym.gym.dtos.request.TrainerRegistrateRequest;
import com.gym.gym.dtos.request.TrainerUpdateRequest;
import com.gym.gym.dtos.response.TrainerFindResponse;
import com.gym.gym.dtos.response.TrainerUpdateResponse;
import com.gym.gym.entities.Trainer;
import com.gym.gym.mappers.TrainerMapper;
import com.gym.gym.services.implementations.TrainerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gym/trainers")
@SuppressWarnings("unused")
public class TrainerController {

    @Autowired
    TrainerServiceImpl trainerHibernateService;

    @Autowired
    TrainerMapper trainerMapper;

    @GetMapping("/username/{username}")
    public ResponseEntity<TrainerFindResponse> getTraineeByUsername(@PathVariable String username){
        Trainer trainer = trainerHibernateService.getTrainerByUsername(username);
        TrainerFindResponse response = trainerMapper.mapToFindResponse(trainer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Credentials> createTrainer(@RequestBody TrainerRegistrateRequest request){
        Trainer trainer = trainerMapper.mapFromRegistrateRequest(request);
        Trainer newTrainer = trainerHibernateService.createTrainer(trainer);
        Credentials credentials = trainerMapper.mapToCredentials(newTrainer);
        return new ResponseEntity<>(credentials, HttpStatus.OK);

    }

    @PostMapping("{username}/update")
    public ResponseEntity<TrainerUpdateResponse> updateTrainer(@PathVariable String username, @RequestBody TrainerUpdateRequest request){
        Trainer trainer = trainerMapper.mapFromUpdateRequest(request);
        Trainer updatedTrainer = trainerHibernateService.updateTrainer(username, trainer);
        TrainerUpdateResponse response = trainerMapper.mapToUpdateResponse(updatedTrainer);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    //TODO: Change toggle endpoint
    @PostMapping("/active/change")
    public ResponseEntity<HttpStatus> changeActiveState(@RequestBody ActiveStateChangeRequest request){
        Boolean activeState = trainerHibernateService.changeActiveState(request.username, request.active);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody PasswordChangeRequest data){
        boolean passwordChanged = trainerHibernateService.changePassword(data.username, data.oldPassword, data.newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
