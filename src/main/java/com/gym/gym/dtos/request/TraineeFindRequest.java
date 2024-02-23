package com.gym.gym.dtos.request;

import com.gym.gym.validators.CheckDateFormat;

public class TraineeFindRequest {
    public String username;
    @CheckDateFormat
    public String periodFrom;
    @CheckDateFormat
    public String periodTo;
    public String trainerName;
    public String trainingTypeName;
}
