package com.gym.gym.mappers;

import com.gym.gym.dtos.request.TrainingCreateRequest;
import com.gym.gym.entities.Training;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TrainingMapper {

    @Mappings({
            @Mapping(source = "traineeUsername", target = "trainee.user.username"),
            @Mapping(source = "trainerUsername", target = "trainer.user.username"),
            @Mapping(source = "trainingDate", target = "trainingDate", dateFormat = "yyyy-MM-dd")
    })
    Training mapFromCreateRequest(TrainingCreateRequest request);
}
