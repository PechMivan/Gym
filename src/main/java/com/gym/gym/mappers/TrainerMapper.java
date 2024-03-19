package com.gym.gym.mappers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.TraineeDTO;
import com.gym.gym.dtos.request.TrainerRegistrateRequest;
import com.gym.gym.dtos.request.TrainerUpdateRequest;
import com.gym.gym.dtos.response.TrainerFindResponse;
import com.gym.gym.dtos.response.TrainerUpdateResponse;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@SuppressWarnings("unused")
public interface TrainerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainees", ignore = true)
    @Mapping(source = "firstname", target = "user.firstname")
    @Mapping(source = "lastname", target = "user.lastname")
    @Mapping(source = "specialization", target = "specialization.name")
    Trainer mapFromRegisterRequest(TrainerRegistrateRequest request);

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.password", target = "password")
    Credentials mapToCredentials(Trainer trainer);

    @Mapping(source = "user.firstname", target = "firstname")
    @Mapping(source = "user.lastname", target = "lastname")
    @Mapping(source = "specialization.name", target = "specialization")
    @Mapping(source = "user.active", target = "isActive")
    TrainerFindResponse mapToFindResponse(Trainer trainer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trainees", ignore = true)
    @Mapping(source = "firstname", target = "user.firstname")
    @Mapping(source = "lastname", target = "user.lastname")
    @Mapping(source = "active", target = "user.isActive")
    @Mapping(source = "specialization", target = "specialization.name")
    Trainer mapFromUpdateRequest(TrainerUpdateRequest request);

    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.firstname", target = "firstname")
    @Mapping(source = "user.lastname", target = "lastname")
    @Mapping(source = "specialization.name", target = "specialization")
    @Mapping(source = "user.active", target = "active")
    TrainerUpdateResponse mapToUpdateResponse(Trainer trainer);

    default TraineeDTO mapToTraineeDTO(Trainee trainee) {
        TraineeDTO traineeDTO = new TraineeDTO();
        traineeDTO.setUsername(trainee.getUser().getUsername());
        traineeDTO.setFirstname(trainee.getUser().getFirstname());
        traineeDTO.setLastname(trainee.getUser().getLastname());
        return traineeDTO;
    }
}
