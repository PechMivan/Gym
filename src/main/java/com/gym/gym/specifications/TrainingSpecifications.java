package com.gym.gym.specifications;

import com.gym.gym.dtos.request.TraineeTrainingFindRequest;
import com.gym.gym.dtos.request.TrainerTrainingFindRequest;
import com.gym.gym.entities.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainingSpecifications {
    public static Specification<Training> getFilteredTrainingsForTrainee(TraineeTrainingFindRequest params) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Training, Trainee> traineeJoin = root.join("trainee");
            Join<Trainee, User> userTraineeJoin = traineeJoin.join("user");

            Join<Training, Trainer> trainerJoin = root.join("trainer");
            Join<Trainer, User> userTrainerJoin = trainerJoin.join("user");

            Join<TrainingType, Training> trainingTypeJoin = root.join("trainingType");

            predicates.add(criteriaBuilder.equal(userTraineeJoin.get("username"), params.username));

            if(params.periodFrom != null && params.periodTo != null){
                Date periodFrom = parseDate(params.periodFrom);
                Date periodTo= parseDate(params.periodTo);
                predicates.add(criteriaBuilder.between(root.get("date"), periodFrom, periodTo));
            } else if (params.periodFrom != null) {
                Date periodFrom = parseDate(params.periodFrom);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), periodFrom));
            } else if (params.periodTo != null) {
                Date periodTo = parseDate(params.periodTo);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), periodTo));
            }

            if(params.trainerName != null){
                predicates.add(criteriaBuilder.equal(userTrainerJoin.get("firstname"), params.trainerName));
            }

            if(params.trainingTypeName != null){
                predicates.add(criteriaBuilder.equal(trainingTypeJoin.get("name"), params.trainingTypeName));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public static Specification<Training> getFilteredTrainingsForTrainer(TrainerTrainingFindRequest params) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<Training, Trainer> trainerJoin = root.join("trainer");
            Join<Trainer, User> userTrainerJoin = trainerJoin.join("user");

            Join<Training, Trainee> traineeJoin = root.join("trainee");
            Join<Trainee, User> userTraineeJoin = traineeJoin.join("user");

            predicates.add(criteriaBuilder.equal(userTrainerJoin.get("username"), params.username));

            if(params.periodFrom != null && params.periodTo != null){
                Date periodFrom = parseDate(params.periodFrom);
                Date periodTo= parseDate(params.periodTo);
                predicates.add(criteriaBuilder.between(root.get("date"), periodFrom, periodTo));
            } else if (params.periodFrom != null) {
                Date periodFrom = parseDate(params.periodFrom);
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), periodFrom));
            } else if (params.periodTo != null) {
                Date periodTo = parseDate(params.periodTo);
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), periodTo));
            }

            if(params.traineeName != null){
                predicates.add(criteriaBuilder.equal(userTraineeJoin.get("firstname"), params.traineeName));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static Date parseDate(String strDate){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException("Couldn't parse date");
        }
    }
}
