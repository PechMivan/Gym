package com.gym.gym.mappers;

import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.dtos.request.TrainingCreateRequest;
import com.gym.gym.dtos.response.TraineeTrainingFindResponse;
import com.gym.gym.dtos.response.TrainerTrainingFindResponse;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import org.mapstruct.*;

import java.util.List;

//TODO: Refactor and optimize mappers by utilizing "uses"
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = { TraineeMapper.class })
@SuppressWarnings("unused")
public interface TrainingMapper {

    @Mappings({
            @Mapping(source = "traineeUsername", target = "trainee.user.username"),
            @Mapping(source = "trainerUsername", target = "trainer.user.username"),
            @Mapping(source = "date", target = "date", dateFormat = "yyyy-MM-dd")
    })
    Training mapFromCreateRequest(TrainingCreateRequest request);

    @Mappings({
            @Mapping(source = "trainer.user.firstname", target = "trainerName"),
            @Mapping(source = "trainingType.name", target = "trainingTypeName")
    })
    TraineeTrainingFindResponse mapToFindTraineeTrainingResponse(Training training);

    List<TraineeTrainingFindResponse> mapToFindTraineeTrainingResponseList(List<Training> training);

    @AfterMapping
    default void setTrainerName(@MappingTarget TraineeTrainingFindResponse response, Training training) {
        Trainer trainer = training.getTrainer();
        response.trainerName = String.format("%s %s", trainer.getUser().getFirstname(), trainer.getUser().getLastname());
    }

    @Mappings({
            @Mapping(source = "trainee.user.firstname", target = "traineeName"),
            @Mapping(source = "trainingType.name", target = "trainingTypeName"),
    })
    TrainerTrainingFindResponse mapToFindTrainerTrainingResponse(Training training);

    List<TrainerTrainingFindResponse> mapToFindTrainerTrainingResponseList(List<Training> training);

    @AfterMapping
    default void setTrainerName(@MappingTarget TrainerTrainingFindResponse response, Training training) {
        Trainee trainee = training.getTrainee();
        response.traineeName = String.format("%s %s", trainee.getUser().getFirstname(), trainee.getUser().getLastname());
    }

    List<TrainerDTO> mapTrainerListToTrainerDTOList(List<Trainer> trainers);
}
