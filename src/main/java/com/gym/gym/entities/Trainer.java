package com.gym.gym.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuperBuilder
public class Trainer extends User implements Serializable {
    private long userId;
    private TrainingType specialization;

    @Override
    public String toString() {
        return super.toString() + "Trainer{" +
                "userId=" + userId +
                ", specialization=" + specialization +
                '}';
    }
}
