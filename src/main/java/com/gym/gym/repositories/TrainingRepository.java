package com.gym.gym.repositories;

import com.gym.gym.entities.Training;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface TrainingRepository extends CrudRepository<Training,Long> {

    @Query("SELECT t" +
            "FROM Training t" +
            "JOIN t.trainee tr" +
            "JOIN tr.user u" +
            "WHERE u.username = :username" +
            "AND t.trainingDate BETWEEN :startDate AND :endDate")
    List<Training> findTrainingsByUsernameAndBetweenDates(String username, String startDate, String endDate);
}
