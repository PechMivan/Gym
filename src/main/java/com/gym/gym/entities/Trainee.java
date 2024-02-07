package com.gym.gym.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@SuperBuilder
@SuppressWarnings("unused")
public class Trainee extends User {
    private long userId;
    private Date dateOfBirth;
    private String address;

    @Override
    public String toString() {
        return super.toString() + "Trainee{" +
                "userId=" + userId +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                '}';
    }
}
