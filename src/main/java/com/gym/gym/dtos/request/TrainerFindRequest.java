package com.gym.gym.dtos.request;

import com.gym.gym.validators.CheckDateFormat;

public class TrainerFindRequest {
    public String username;
    @CheckDateFormat
    public String periodFrom;
    @CheckDateFormat
    public String periodTo;
    public String traineeName;
}
