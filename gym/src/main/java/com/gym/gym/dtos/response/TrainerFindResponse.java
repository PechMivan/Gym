package com.gym.gym.dtos.response;

import com.gym.gym.dtos.TraineeDTO;

import java.util.List;

public class TrainerFindResponse {
    public String firstname;
    public String lastname;
    public String specialization;
    public boolean isActive;
    public List<TraineeDTO> trainees;
}
