package com.gym.gym.mappers;

import com.gym.gym.dtos.request.TrainingCreateRequest;
import com.gym.gym.dtos.response.TraineeTrainingFindResponse;
import com.gym.gym.dtos.response.TrainerTrainingFindResponse;
import com.gym.gym.entities.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
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

    @Mappings({
            @Mapping(source = "trainee.user.firstname", target = "traineeName"),
            @Mapping(source = "trainingType.name", target = "trainingTypeName")
    })
    TrainerTrainingFindResponse mapToFindTrainerTrainingResponse(Training training);
}
