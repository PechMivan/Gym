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

		callTraineeService();
		callTrainerService();
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
		traineeService.saveTrainee(traineeTest1);
		traineeService.saveTrainee(traineeTest2);
		List<Trainee> traineeList = traineeService.getAllTrainees();
		traineeList.forEach(trainee -> System.out.println(trainee.getUsername()));
		Optional<Trainee> trainee = traineeService.getTraineeById(2L);
		trainee.ifPresentOrElse(t -> System.out.println(t.toString()),
				() -> System.out.println("Trainee not found!"));
		traineeService.deleteTrainee(2L);
		trainee = traineeService.getTraineeById(2L);
		trainee.ifPresentOrElse(t -> System.out.println(t.toString()),
				() -> System.out.println("Trainee not found!"));

		Trainee UpdatedTrainee = Trainee.builder()
				.firstName("testName")
				.lastName("testLastname")
				.username("user")
				.password("pass")
				.dateOfBirth(new Date())
				.isActive(true)
				.userId(2L)
				.build();

		traineeService.updateTrainee(1L, UpdatedTrainee);
		trainee = traineeService.getTraineeById(1L);
		trainee.ifPresentOrElse(t -> System.out.println(t.toString()),
				() -> System.out.println("Trainee not found!"));
	}

	public static void callTrainerService(){
		Trainer trainerTest1 = Trainer.builder()
				.firstName("Mario")
				.lastName("Pech")
				.username("Mapech")
				.password("pass")
				.specialization(TrainingType.YOGA)
				.isActive(true)
				.userId(1L)
				.build();
		Trainer trainerTest2 = Trainer.builder()
				.firstName("Mario")
				.lastName("Perez")
				.username("anotherpECH")
				.password("pass")
				.specialization(TrainingType.HIIT)
				.isActive(false)
				.userId(2L)
				.build();
		trainerService.saveTrainer(trainerTest1);
		trainerService.saveTrainer(trainerTest2);

		List<Trainer> trainerList = trainerService.getAllTrainers();
		trainerList.forEach(trainer -> System.out.println(trainer.getUsername()));
		Optional<Trainer> trainer = trainerService.getTrainerById(2L);
		trainer.ifPresentOrElse(t -> System.out.println(t.toString()),
				() -> System.out.println("Trainer not found!"));
		trainerService.deleteTrainer(2L);
		trainer = trainerService.getTrainerById(2L);
		trainer.ifPresentOrElse(t -> System.out.println(t.toString()),
				() -> System.out.println("Trainer not found!"));
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
		trainingService.createTraining(trainingTest1);
		trainingService.createTraining(trainingTest2);

		Optional<Training> training = trainingService.getTrainingById(1L);
		training.ifPresentOrElse(t -> System.out.println(t.toString()),
								 () -> System.out.println("Training not found!"));

		training = trainingService.getTrainingById(2L);
		training.ifPresentOrElse(t -> System.out.println(t.toString()),
				() -> System.out.println("Training not found!"));

		training = trainingService.getTrainingById(3L);
		training.ifPresentOrElse(t -> System.out.println(t.toString()),
				() -> System.out.println("Training not found!"));
	}


}
