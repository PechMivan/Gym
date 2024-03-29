package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.CredentialsAndAccessToken;
import com.gym.gym.dtos.request.TrainerRegistrateRequest;
import com.gym.gym.dtos.request.TrainerUpdateRequest;
import com.gym.gym.dtos.response.TrainerFindResponse;
import com.gym.gym.dtos.response.TrainerUpdateResponse;
import com.gym.gym.entities.Token;
import com.gym.gym.entities.Trainer;
import com.gym.gym.mappers.TrainerMapper;
import com.gym.gym.services.implementations.TrainerServiceImpl;
import io.micrometer.core.annotation.Timed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("gym/trainers")
@RequiredArgsConstructor
@Validated
@SuppressWarnings("unused")
public class TrainerController {

    private final TrainerServiceImpl trainerService;
    private final TrainerMapper trainerMapper;

    @GetMapping("{username}")
    public ResponseEntity<TrainerFindResponse> getTrainerByUsername(@PathVariable String username){
        Trainer trainer = trainerService.getTrainerByUsername(username);
        TrainerFindResponse response = trainerMapper.mapToFindResponse(trainer);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    @Timed(value = "create-trainer.time", description = "Time taken to create a trainer")
    public ResponseEntity<CredentialsAndAccessToken> createTrainer(@RequestBody @Valid TrainerRegistrateRequest request){
        Trainer trainer = trainerMapper.mapFromRegisterRequest(request);
        Trainer newTrainer = trainerService.createTrainer(trainer);
        Token accessToken = newTrainer.getUser().getTokens().get(0);
        CredentialsAndAccessToken newCredentials = new CredentialsAndAccessToken
        (
            new Credentials(
                    newTrainer.getUser().getUsername(),
                    newTrainer.getUser().getPassword()
            ),
            accessToken.getToken()
        );
        return new ResponseEntity<>(newCredentials, HttpStatus.OK);

    }

    @PutMapping("{username}")
    public ResponseEntity<TrainerUpdateResponse> updateTrainer(@PathVariable String username, @RequestBody @Valid TrainerUpdateRequest request){
        Trainer trainer = trainerMapper.mapFromUpdateRequest(request);
        Trainer updatedTrainer = trainerService.updateTrainer(username, trainer);
        TrainerUpdateResponse response = trainerMapper.mapToUpdateResponse(updatedTrainer);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
