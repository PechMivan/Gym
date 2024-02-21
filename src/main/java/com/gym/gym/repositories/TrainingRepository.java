package com.gym.gym.repositories;

import com.gym.gym.entities.Trainer;
import com.gym.gym.entities.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training,Long> {

    @Query("SELECT t " +
            "FROM Training t " +
            "JOIN t.trainee tr " +
            "JOIN tr.user u " +
            "WHERE u.username = :username " +
            "AND t.date BETWEEN :startDate AND :endDate")
    List<Training> findAllTrainingsByTraineeUsernameAndBetweenDates(@Param("username") String username,
                                                                    @Param("startDate") Date startDate,
                                                                    @Param("endDate") Date endDate);

    @Query("SELECT t " +
            "FROM Training t " +
            "JOIN t.trainer tr " +
            "JOIN tr.user u " +
            "WHERE u.username = :username " +
            "AND t.date BETWEEN :startDate AND :endDate")
    List<Training> findAllTrainingsByTrainerUsernameAndBetweenDates(@Param("username") String username,
                                                                    @Param("startDate") Date startDate,
                                                                    @Param("endDate") Date endDate);

    @Query("SELECT t FROM Training t " +
            "JOIN t.trainee tr " +
            "JOIN tr.user tu " +
            "JOIN t.trainer tn " +
            "JOIN tn.user tnu " +
            "WHERE tu.username = :traineeUsername " +
            "AND tnu.firstname = :trainerName")
    List<Training> findAllTrainingsByTraineeUsernameAndTrainerName(@Param("traineeUsername") String traineeUsername,
                                                                   @Param("trainerName") String trainerName);

    @Query("SELECT t FROM Training t " +
            "JOIN t.trainer tr " +
            "JOIN tr.user tu " +
            "JOIN t.trainee tn " +
            "JOIN tn.user tnu " +
            "WHERE tu.username = :trainerUsername " +
            "AND tnu.firstname = :traineeName")
    List<Training> findAllTrainingsByTrainerUsernameAndTraineeName(@Param("trainerUsername") String trainerUsername,
                                                                   @Param("traineeName") String traineeName);

    @Query("SELECT t FROM Training t " +
            "JOIN t.trainee tr " +
            "JOIN tr.user tu " +
            "JOIN t.trainingType tt " +
            "WHERE tu.username = :traineeUsername " +
            "AND tt.name = :trainingType")
    List<Training> findAllTrainingsByTraineeUsernameAndTrainingType(@Param("traineeUsername") String traineeUsername,
                                                                   @Param("trainingType") String trainingType);
}
