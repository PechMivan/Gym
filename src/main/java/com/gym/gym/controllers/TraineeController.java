package com.gym.gym.controllers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.PasswordChangeRequest;
import com.gym.gym.dtos.request.TraineeRegistrateRequest;
import com.gym.gym.dtos.response.TraineeFindResponse;
import com.gym.gym.entities.Trainee;
import com.gym.gym.mappers.TraineeMapper;
import com.gym.gym.services.implementations.TraineeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/gym/trainees")
@SuppressWarnings("unused")
public class TraineeController {

    @Autowired
    TraineeServiceImpl traineeHibernateService;

    @Autowired
    TraineeMapper traineeMapper;

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
    public ResponseEntity< TraineeFindResponse > getTraineeByUsername(@PathVariable String username){
        Trainee trainee = traineeHibernateService.getTraineeByUsername(username);
        TraineeFindResponse response = traineeMapper.mapToFindResponse(trainee);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Trainee>> getAllTrainees(){
        List<Trainee> trainees = traineeHibernateService.getAllTrainees();
        return new ResponseEntity<>( trainees, HttpStatus.OK ); // Status 200
    }

    @PostMapping
    public  ResponseEntity<Credentials> createTrainee(@RequestBody TraineeRegistrateRequest data) {
        Trainee trainee = traineeMapper.mapFromRegistrateRequest(data);
        Trainee newTrainee = traineeHibernateService.createTrainee(trainee);
        Credentials newCredentials = traineeMapper.mapToCredentials(newTrainee);
        return new ResponseEntity<>(newCredentials, HttpStatus.CREATED); // Status 201
    }

//    @PostMapping("{id}/update")
//    public ResponseEntity<Trainee> updateTrainee(@PathVariable Long id, @RequestBody TraineeDTO data){
//        Trainee updatedTrainee = traineeHibernateService.updateTrainee(id, data);
//        return new ResponseEntity<>(updatedTrainee, HttpStatus.OK); // Status 200
//    }

    @PostMapping("/username/{username}/delete")
    public ResponseEntity<String> deleteTraineeByUsername(@PathVariable String username, @RequestBody Credentials credentials){
        long count = traineeHibernateService.deleteTraineeByUsername(username, credentials);
        if(count > 0){
            return new ResponseEntity<>("User succesfully deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User doesn't exist", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("{id}/toggle")
    public ResponseEntity<String> toggleTraineeActive(@PathVariable long id, @RequestBody Credentials credentials){
        Boolean activeState = traineeHibernateService.toggleTraineeActive(id, credentials);
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
