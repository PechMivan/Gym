package com.gym.gym.controllers;

import com.gym.gym.entities.TrainingType;
import com.gym.gym.services.TrainingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("gym/training-type")
@RequiredArgsConstructor
public class TrainingTypeController {

    private final TrainingTypeService trainingTypeService;

    @GetMapping
    public ResponseEntity< List<TrainingType> > getAllTrainingTypes(){
        List<TrainingType> trainingTypes = trainingTypeService.getAllTrainingTypes();
        return new ResponseEntity<>(trainingTypes, HttpStatus.OK);
    }
}
