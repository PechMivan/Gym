package com.gym.gym.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Trainer extends User implements Serializable {
    private long userId;
    private TrainingType specialization;
}
