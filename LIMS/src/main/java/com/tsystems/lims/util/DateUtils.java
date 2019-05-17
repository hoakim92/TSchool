package com.tsystems.lims.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class DateUtils {
    public static String dateTimeFormat = "yyyy-MM-dd'T'HH:mm";
    public static String timeFormat = "HH:mm";
    public static String delimiter = ";";

    public static void validateDate(String[] dates, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        Arrays.asList(dates).forEach(d -> {
            try {
                sdf.parse(d);
            } catch (ParseException e) {
                throw new UnsupportedOperationException("Invalid date format");
            }
        });
    }
}
