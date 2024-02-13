package com.gym.gym.controllers;

import com.gym.gym.services.implementations.TraineeHibernateServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class TrainerHibernateController {

    @Autowired
    TraineeHibernateServiceImpl traineeHibernateService;


}
