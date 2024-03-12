package com.gym.gym.services.implementations;

import com.gym.gym.clients.Workload;
import com.gym.gym.clients.WorkloadServiceClient;
import com.gym.gym.dtos.request.TraineeTrainingFindRequest;
import com.gym.gym.dtos.request.TrainerTrainingFindRequest;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import com.gym.gym.entities.TrainingType;
import com.gym.gym.exceptions.NotFoundException;
import com.gym.gym.repositories.TrainingRepository;
import com.gym.gym.services.TrainingService;
import com.gym.gym.specifications.TrainingSpecifications;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class TrainingServiceImpl implements TrainingService {

    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    TrainingTypeServiceImpl trainingTypeService;
    @Autowired
    TrainerServiceImpl trainerService;
    @Autowired
    TraineeServiceImpl traineeService;

    private final WorkloadServiceClient workloadServiceClient;

    @Override
    public Training getTrainingById(long id) {
        return trainingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Training with id %d not found", id)));
    }

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    @Transactional
    public void saveTraining(Training training){
        trainingRepository.save(training);
    }

    @Override
    public Training createTraining(Training training) {
        Trainee existingTrainee = traineeService.getTraineeByUsername(training.getTrainee().getUser().getUsername());
        Trainer existingTrainer = trainerService.getTrainerByUsername(training.getTrainer().getUser().getUsername());
        TrainingType trainingType = existingTrainer.getSpecialization();

        Training newTraining = Training.builder()
                                .trainee(existingTrainee)
                                .trainer(existingTrainer)
                                .trainingType(trainingType)
                                .name(training.getName())
                                .date(training.getDate())
                                .duration(training.getDuration())
                                .build();

        saveTraining(newTraining);
        Workload workload = Workload.builder()
                        .username(newTraining.getTrainer().getUser().getUsername())
                        .firstname(newTraining.getTrainer().getUser().getFirstname())
                        .lastname(newTraining.getTrainer().getUser().getLastname())
                        .isActive(newTraining.getTrainer().getUser().isActive())
                        .trainingDate("2020-08-05")
                        .trainingDuration(newTraining.getDuration())
                        .actionType("ADD")
                        .build();
        workloadServiceClient.updateWorkload(workload);
        traineeService.updateTrainersList(existingTrainee.getId(), existingTrainer);
        log.info(String.format("Training successfully created with id %d ", newTraining.getId()));
        return newTraining;
    }

    @Override
    public List<Trainer> getAllTrainersNotInTraineeTrainersListByUsername(String username){
        Trainee trainee = traineeService.getTraineeByUsername(username);
        List<Trainer> trainers = trainerService.getAllTrainers();
        List<Trainer> trainersOfTrainee = trainee.getTrainers();

        trainers = trainers.stream()
                .filter(trainer -> !trainersOfTrainee.contains(trainer) && trainer.getUser().isActive())
                .collect(Collectors.toList());

        return trainers;
    }

    @Override
    public List<Training> getFilteredTrainingsForTrainee(TraineeTrainingFindRequest params) {
        Specification<Training> specification = TrainingSpecifications.getFilteredTrainingsForTrainee(params);
        return trainingRepository.findAll(specification);
    }

    @Override
    public List<Training> getFilteredTrainingsForTrainer(TrainerTrainingFindRequest params) {
        Specification<Training> specification = TrainingSpecifications.getFilteredTrainingsForTrainer(params);
        return trainingRepository.findAll(specification);
    }
}
