package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.PasswordChangeRequest;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.entities.Trainer;
import com.gym.gym.services.implementations.TrainerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gym/trainers")
@SuppressWarnings("unused")
public class TrainerController {

    @Autowired
    TrainerServiceImpl trainerHibernateService;


    @GetMapping("{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable long id){
        Trainer trainer = trainerHibernateService.getTrainerById(id);
        if(trainer != null){
            return new ResponseEntity<>(trainer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Trainer> getTraineeByUsername(@PathVariable String username){
        Trainer trainer = trainerHibernateService.getTrainerByUsername(username);
        if(trainer != null){
            return new ResponseEntity<>(trainer, HttpStatus.OK); // Status 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Status 404
        }
    }

    @GetMapping
    public ResponseEntity<List<Trainer>> getAllTrainers(){
        List<Trainer> trainers = trainerHibernateService.getAllTrainers();
        return new ResponseEntity<>(trainers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Trainer> createTrainer(@RequestBody TrainerDTO data){
        Trainer trainer = trainerHibernateService.createTrainer(data);
        if(trainer != null){
            return new ResponseEntity<>(trainer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{id}/update")
    public ResponseEntity<Trainer> createTrainer(@PathVariable long id, @RequestBody TrainerDTO data){
        Trainer trainer = trainerHibernateService.updateTrainer(id, data);
        if(trainer != null){
            return new ResponseEntity<>(trainer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{id}/toggle")
    public ResponseEntity<String> toggleTraineeActive(@PathVariable long id, @RequestBody Credentials credentials){
        Boolean activeState = trainerHibernateService.toggleTraineeActive(id, credentials);
        if(activeState == null){
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Active status changed to: " + activeState, HttpStatus.OK);
        }
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest data){
        boolean passwordChanged = trainerHibernateService.changePassword(data.username, data.oldPassword, data.newPassword);
        if(passwordChanged){
            return new ResponseEntity<>("Password changed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Password not changed", HttpStatus.UNAUTHORIZED);
        }
    }
}
