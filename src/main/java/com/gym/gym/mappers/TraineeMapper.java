package com.gym.gym.mappers;

import com.gym.gym.dtos.Credentials;
import com.gym.gym.dtos.TrainerDTO;
import com.gym.gym.dtos.request.TraineeRegistrateRequest;
import com.gym.gym.dtos.request.TraineeUpdateRequest;
import com.gym.gym.dtos.response.TraineeFindResponse;
import com.gym.gym.dtos.response.TraineeUpdateResponse;
import com.gym.gym.entities.Trainee;
import com.gym.gym.entities.Trainer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

import java.util.ArrayList;
import java.util.List;

//TODO: Implementar los respectivos uses mappers para hacer más eficiente.
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
@SuppressWarnings("unused")
public interface TraineeMapper {

    @Mappings({
            @Mapping(source = "firstname", target = "user.firstname"),
            @Mapping(source = "lastname", target = "user.lastname"),
            @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    })
    Trainee mapFromRegistrateRequest(TraineeRegistrateRequest request);

    @Mappings({
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "user.password", target = "password")
    })
    Credentials mapToCredentials(Trainee trainee);

    @Mappings({
            @Mapping(source = "user.firstname", target = "firstname"),
            @Mapping(source = "user.lastname", target = "lastname"),
            @Mapping(source = "user.active", target = "active")
    })
    TraineeFindResponse mapToFindResponse(Trainee trainee);

//    default List<TrainerDTO> mapToTrainerDTOList(List<Trainer> trainers) {
//        if(trainers == null){
//            return null;
//        }
//
//        List<TrainerDTO> trainerDTOS = new ArrayList<>();
//        TrainerDTO trainerDTO = new TrainerDTO();
//
//        for(Trainer trainer : trainers){
//            trainerDTO.setUsername(trainer.getUser().getUsername());
//            trainerDTO.setFirstname(trainer.getUser().getFirstname());
//            trainerDTO.setLastname(trainer.getUser().getLastname());
//            trainerDTO.setSpecialization(trainer.getSpecialization().getName());
//            trainerDTOS.add(trainerDTO);
//        }

 //       return trainerDTOS;
 //   }

    //TODO: Pasar a Trainer y conectarlos
    default TrainerDTO mapToTrainerDTO(Trainer trainer) {
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setUsername(trainer.getUser().getUsername());
        trainerDTO.setFirstname(trainer.getUser().getFirstname());
        trainerDTO.setLastname(trainer.getUser().getLastname());
        trainerDTO.setSpecialization(trainer.getSpecialization().getName());
        return trainerDTO;
    }

    @Mappings({
            @Mapping(source = "firstname", target = "user.firstname"),
            @Mapping(source = "lastname", target = "user.lastname"),
            @Mapping(source = "active", target = "user.isActive"),
            @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    })
    Trainee mapFromUpdateRequest(TraineeUpdateRequest request);

    @Mappings({
            @Mapping(source = "user.username", target = "username"),
            @Mapping(source = "user.firstname", target = "firstname"),
            @Mapping(source = "user.lastname", target = "lastname"),
            @Mapping(source = "user.active", target = "active"),
            @Mapping(source = "dateOfBirth", target = "dateOfBirth", dateFormat = "yyyy-MM-dd")
    })
    TraineeUpdateResponse mapToUpdateResponse(Trainee trainee);

    //    @Mappings({
    //        @Mapping(source = "user.username", target = "username"),
//            @Mapping(source = "user.firstname", target = "firstname"),
//            @Mapping(source = "user.lastname", target = "lastname"),
//            @Mapping(source = "user.active", target = "isActive")
//    })
//    TraineeFindResponse mapToUpdateResponse(Trainee trainee);

//TODO: hacer el mapping a TraineeDTO
}
