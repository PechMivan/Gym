package com.gym.gym.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class Trainee extends User implements Serializable {
    private long userId;
    private Date dateOfBirth;
    private String address;
}
