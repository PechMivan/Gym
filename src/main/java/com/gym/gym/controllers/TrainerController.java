package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
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
    TrainerServiceImpl trainerService;

    @Autowired
    TrainerMapper trainerMapper;

    @GetMapping("/username/{username}")
    public ResponseEntity<TrainerFindResponse> getTraineeByUsername(@PathVariable String username){
        Trainer trainer = trainerService.getTrainerByUsername(username);
        TrainerFindResponse response = trainerMapper.mapToFindResponse(trainer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Credentials> createTrainer(@RequestBody TrainerRegistrateRequest request){
        Trainer trainer = trainerMapper.mapFromRegistrateRequest(request);
        Trainer newTrainer = trainerService.createTrainer(trainer);
        Credentials credentials = trainerMapper.mapToCredentials(newTrainer);
        return new ResponseEntity<>(credentials, HttpStatus.OK);

    }

    @PostMapping("{username}/update")
    public ResponseEntity<TrainerUpdateResponse> updateTrainer(@PathVariable String username, @RequestBody TrainerUpdateRequest request){
        Trainer trainer = trainerMapper.mapFromUpdateRequest(request);
        Trainer updatedTrainer = trainerService.updateTrainer(username, trainer, request.credentials);
        TrainerUpdateResponse response = trainerMapper.mapToUpdateResponse(updatedTrainer);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
