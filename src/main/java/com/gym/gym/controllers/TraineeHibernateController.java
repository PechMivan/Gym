package com.gym.gym.controllers;

import com.gym.gym.dtos.PasswordChangeRequest;
import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.services.implementations.TraineeHibernateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/gym/trainees")
@SuppressWarnings("unused")
public class TraineeHibernateController {

    @Autowired
    TraineeHibernateServiceImpl traineeHibernateService;

    @GetMapping("{id}")
    public ResponseEntity< Trainee > getTraineeById(@PathVariable Long id){
        Trainee trainee = traineeHibernateService.getTraineeById(id);
        if(trainee != null){
            return new ResponseEntity<>(trainee, HttpStatus.OK); // Status 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND ); // Status 404
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity< Trainee > getTraineeByUsername(@PathVariable String username){
        Trainee trainee = traineeHibernateService.getTraineeByUsername(username);
        if(trainee != null){
            return new ResponseEntity<>(trainee, HttpStatus.OK); // Status 200
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Status 404
        }
    }

    @GetMapping
    public ResponseEntity<List<Trainee>> getAllTrainees(){
        List<Trainee> trainees = traineeHibernateService.getAllTrainees();
        return new ResponseEntity<>( trainees, HttpStatus.OK ); // Status 200
    }

    @PostMapping
    public  ResponseEntity<Trainee> createTrainee(@RequestBody TraineeDTO data) {
        Trainee newTrainee = traineeHibernateService.createTrainee(data);
        return new ResponseEntity<>(newTrainee, HttpStatus.CREATED); // Status 201
    }

    @PostMapping("{id}/update")
    public ResponseEntity<Trainee> updateTrainee(@PathVariable Long id, @RequestBody TraineeDTO data){
        Trainee updatedTrainee = traineeHibernateService.updateTrainee(id, data);
        return new ResponseEntity<>(updatedTrainee, HttpStatus.OK); // Status 200
    }

    @PostMapping("/username/{username}/delete")
    public ResponseEntity<String> deleteTraineeByUsername(@PathVariable String username){
        long count = traineeHibernateService.deleteTraineeByUsername(username);
        if(count > 0){
            return new ResponseEntity<>("User succesfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{id}/toggle")
    public ResponseEntity<String> toggleTraineeActive(@PathVariable long id){
        Boolean activeState = traineeHibernateService.toggleTraineeActive(id);
        if(activeState == null){
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>("Active status changed to: " + activeState, HttpStatus.OK);
        }
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest data){
        boolean passwordChanged = traineeHibernateService.changePassword(data.username, data.oldPassword, data.newPassword);
        if(passwordChanged){
            return new ResponseEntity<>("Password changed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Password not changed", HttpStatus.UNAUTHORIZED);
        }
    }

}
