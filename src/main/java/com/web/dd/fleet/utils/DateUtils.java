package com.web.dd.fleet.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static LocalDate parseDate(String date) {
        return date == null ? null : LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public static String toString(LocalDate date) {
        return date == null ? null : date.format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }
}
