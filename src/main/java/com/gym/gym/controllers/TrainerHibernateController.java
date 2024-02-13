package com.gym.gym.controllers;

import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.entities.Trainer;
import com.gym.gym.services.implementations.TrainerHibernateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gym/trainers")
public class TrainerHibernateController {

    @Autowired
    TrainerHibernateServiceImpl trainerHibernateService;


    @GetMapping("{id}")
    public ResponseEntity<Trainer> getTrainerById(@PathVariable long id){
        Trainer trainer = trainerHibernateService.getTrainerById(id);
        if(trainer != null){
            return new ResponseEntity<>(trainer, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
}
