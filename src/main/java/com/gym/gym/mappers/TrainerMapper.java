package com.gym.gym.mappers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.request.TrainerRegistrateRequest;
import com.gym.gym.dtos.request.TrainerUpdateRequest;
import com.gym.gym.entities.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@SuppressWarnings("unused")
public interface TrainerMapper {

    @Mappings({
            @Mapping(source = "firstname", target = "user.firstname"),
            @Mapping(source = "lastname", target = "user.lastname"),
            @Mapping(source = "specialization", target = "specialization.name")
    })
    Trainer mapFromRegistrateRequest(TrainerRegistrateRequest request);

    @Mappings({
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "user.password", target = "password")
    })
    Credentials mapToCredentials(Trainer trainer);

    //    @Mappings({
//            @Mapping(source = "user.firstname", target = "firstname"),
//            @Mapping(source = "user.lastname", target = "lastname"),
//            @Mapping(source = "specialization.name", target = "specialization"),
//            @Mapping(source = "user.active", target = "isActive")
//    })
//    TrainerFindResponse mapToFindResponse(Trainer trainer);

    @Mappings({
            @Mapping(source = "username", target = "user.username"),
            @Mapping(source = "firstname", target = "user.firstname"),
            @Mapping(source = "lastname", target = "user.lastname"),
            @Mapping(source = "isActive", target = "user.isActive"),
            @Mapping(source = "specialization", target = "specialization.name")

    })
    Trainer mapFromUpdateRequest(TrainerUpdateRequest request);

    //    @Mappings({
    //        @Mapping(source = "user.username", target = "username"),
//            @Mapping(source = "user.firstname", target = "firstname"),
//            @Mapping(source = "user.lastname", target = "lastname"),
//            @Mapping(source = "user.active", target = "isActive")
//    })
//    TrainerFindResponse mapToUpdateResponse(Trainer trainer);
}
