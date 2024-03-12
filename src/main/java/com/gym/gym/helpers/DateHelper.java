package com.gym.gym.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class DateHelper {

    public static Date parseDate(String dateString){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Couldn't parse date");
        }
    }
}
