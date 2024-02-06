package com.gym.gym.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@SuppressWarnings("unused")
public class Trainee extends User implements Serializable {
    private long userId;
    private Date dateOfBirth;
    private String address;

}
