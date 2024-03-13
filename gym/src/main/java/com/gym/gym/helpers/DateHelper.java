package com.gym.gym.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class DateHelper {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static Date parseString(String dateString){
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException("Couldn't parse date");
        }
    }

    public static String parseDate(Date date){
        return formatter.format(date);
    }
}
