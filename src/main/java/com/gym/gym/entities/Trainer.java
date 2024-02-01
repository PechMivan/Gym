package com.gym.gym.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Trainer extends User {
    private long userId;
    private TrainingType specialization;
}
