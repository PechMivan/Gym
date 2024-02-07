package com.gym.gym;

import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.services.TraineeService;
import com.gym.gym.services.TrainerService;
import com.gym.gym.services.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class GymApplication {

	private static TraineeService traineeService;
	private static TrainerService trainerService;
	private static TrainingService trainingService;

	@Autowired
	public GymApplication(TraineeService traineeService, TrainerService trainerService, TrainingService trainingService){
		GymApplication.traineeService = traineeService;
		GymApplication.trainerService = trainerService;
		GymApplication.trainingService = trainingService;
	}

	public static void main(String[] args) {

		SpringApplication.run(GymApplication.class, args);

		System.out.println("------- Trainee Service --------");
		callTraineeService();
		System.out.println("------- Trainer Service --------");
		callTrainerService();
		System.out.println("------- Training Service --------");
		callTrainingService();

	}

	public static void callTraineeService(){
		Trainee traineeTest1 = Trainee.builder()
				.firstName("Mario")
				.lastName("Pech")
				.address("Street 20")
				.username("Mapech")
				.password("pass")
				.dateOfBirth(new Date())
				.isActive(true)
				.userId(1L)
				.build();
		Trainee traineeTest2 = Trainee.builder()
				.firstName("Mario")
				.lastName("Pech")
				.address("Street 20")
				.username("user")
				.password("pass")
				.dateOfBirth(new Date())
				.isActive(true)
				.userId(2L)
				.build();
		Trainee traineeTest3 = Trainee.builder()
				.firstName("Mario")
				.lastName("Pech")
				.address("Street 20")
				.username("user")
				.password("pass")
				.dateOfBirth(new Date())
				.isActive(true)
				.userId(3L)
				.build();

		System.out.println("Creating 3 trainees...");
		traineeService.createTrainee(traineeTest1);
		traineeService.createTrainee(traineeTest2);
		traineeService.createTrainee(traineeTest3);
		System.out.println("Getting all trainees...");
		List<Trainee> traineeList = traineeService.getAllTrainees();
		System.out.println("List of trainees:");
		traineeList.forEach(trainee -> System.out.println(trainee.toString()));
		System.out.println("Finding trainee by id (2): ");
		Trainee trainee = traineeService.getTraineeById(2L);
		System.out.println(trainee.toString());
		System.out.println("Deleting trainee by id (2)...");
		traineeService.deleteTrainee(2L);

		System.out.println("Setting up a new Trainee instance...");
		Trainee UpdatedTrainee = Trainee.builder()
				.lastName("testLastname")
				.username("user")
				.password("pass")
				.dateOfBirth(new Date())
				.isActive(true)
				.userId(1L)
				.build();

		System.out.println("Updating Trainee with id (1)...");
		traineeService.updateTrainee(1L, UpdatedTrainee);
		trainee = traineeService.getTraineeById(1L);
		System.out.println("Updated trainee with id (1): ");
		System.out.println(trainee.toString());
	}

	public static void callTrainerService(){
		Trainer trainerTest1 = Trainer.builder()
				.firstName("mario")
				.lastName("pech")
				.username("Mapech")
				.password("pass")
				.specialization(TrainingType.YOGA)
				.isActive(true)
				.userId(1L)
				.build();
		Trainer trainerTest2 = Trainer.builder()
				.firstName("mario")
				.lastName("perez")
				.username("anotherpECH")
				.password("pass")
				.specialization(TrainingType.HIIT)
				.isActive(false)
				.userId(2L)
				.build();
		Trainer trainerTest3 = Trainer.builder()
				.firstName("mario")
				.lastName("pech")
				.username("pECH")
				.password("asdas")
				.specialization(TrainingType.CARDIO)
				.isActive(false)
				.userId(3L)
				.build();

		System.out.println("Creating 3 trainers...");
		trainerService.createTrainer(trainerTest1);
		trainerService.createTrainer(trainerTest2);
		trainerService.createTrainer(trainerTest3);

		System.out.println("Getting all trainers...");
		List<Trainer> trainerList = trainerService.getAllTrainers();
		System.out.println("List of trainers: ");
		trainerList.forEach(trainer -> System.out.println(trainer.toString()));
		System.out.println("Finding trainer with id (2):");
		Trainer trainer = trainerService.getTrainerById(2L);
		System.out.println(trainer.toString());
		System.out.println("Deleting trainer with id (2)...");
		trainerService.deleteTrainer(2L);

		System.out.println("Setting up a new Trainer instance...");
		Trainer updateTrainer = Trainer.builder()
				.firstName("Mario")
				.lastName("Pech")
				.username("usernameTest")
				.password("usernamePass")
				.specialization(TrainingType.SPINNING)
				.isActive(false)
				.userId(1L)
				.build();

		System.out.println("Updating trainer with id (1)...");
		trainerService.updateTrainer(1L, updateTrainer);
		trainer = trainerService.getTrainerById(1L);
		System.out.println("Updated trainer: ");
		System.out.println(trainer.toString());
	}

	public static void callTrainingService(){
		Training trainingTest1 = Training.builder()
				.trainingId(1L)
				.traineeId(1L)
				.trainerId(1L)
				.trainingName("Super-Cardio")
				.trainingType(TrainingType.CARDIO)
				.trainingDate(new Date())
				.trainingDuration(10)
				.build();
		Training trainingTest2 = Training.builder()
				.trainingId(2L)
				.traineeId(1L)
				.trainerId(1L)
				.trainingName("Full-Strenght")
				.trainingType(TrainingType.STRENGTH)
				.trainingDate(new Date())
				.trainingDuration(15)
				.build();

		System.out.println("Creating 2 trainings...");
		trainingService.createTraining(trainingTest1);
		trainingService.createTraining(trainingTest2);

		System.out.println("Getting training with id (1): ");
		Training training = trainingService.getTrainingById(1L);
		System.out.println(training.toString());

		System.out.println("Getting training with id (2): ");
		training = trainingService.getTrainingById(2L);
		System.out.println(training.toString());
	}


}
