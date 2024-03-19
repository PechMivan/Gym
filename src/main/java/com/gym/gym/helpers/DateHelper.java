package com.gym.gym.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class DateHelper {

    private static final String FORMAT = "yyyy-MM-dd";

    private DateHelper(){}

    public static Date parseDateString(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        try {
            return formatter.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e); // TODO: throw something more meaningful
        }
    }

    public static String parseDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);
        return formatter.format(date);
    }
}
