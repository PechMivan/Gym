package com.gym.gym;

import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import com.gym.gym.services.TraineeService;
import com.gym.gym.services.TrainerService;
import com.gym.gym.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
public class GymApplication {
	public static void main(String[] args) {
		SpringApplication.run(GymApplication.class, args);
	}
}
