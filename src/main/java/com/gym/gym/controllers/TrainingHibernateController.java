package com.gym.gym.controllers;

import com.gym.gym.dtos.TrainingDTO;
import com.gym.gym.entities.Training;
import com.gym.gym.services.implementations.TrainingHibernateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gym/trainings")
@SuppressWarnings("unused")
public class TrainingHibernateController {

    @Autowired
    TrainingHibernateServiceImpl trainingHibernateService;

    @PostMapping
    public ResponseEntity<Training> createTraining(@RequestBody TrainingDTO data){
        Training training = trainingHibernateService.createTraining(data);
        if (training != null){
            return new ResponseEntity<>(training, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
