package com.gym.gym.controllers;

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
public class TraineeHibernateController {

    @Autowired
    TraineeHibernateServiceImpl traineeHibernateService;

    @GetMapping("{id}")
    public ResponseEntity< Trainee > getTraineeById(@PathVariable Long id){
        Trainee trainee = traineeHibernateService.getTraineeById(id);
        return new ResponseEntity<>(trainee, HttpStatus.OK); // Status 200
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

    @PostMapping("{id}")
    public ResponseEntity<Trainee> updateTrainee(@PathVariable Long id, @RequestBody TraineeDTO data){
        Trainee updatedTrainee = traineeHibernateService.updateTrainee(id, data);
        return new ResponseEntity<>(updatedTrainee, HttpStatus.OK); // Status 200
    }

}
