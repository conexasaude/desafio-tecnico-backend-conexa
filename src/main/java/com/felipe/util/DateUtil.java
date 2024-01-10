package com.felipe.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final String DATE_PATTERN_BR = "dd/MM/yyyy";
    private static final String DATE_TIME_PATTERN_BR = "dd/MM/yyyy HH:mm:ss";
    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static LocalDate convertStringToLocalDate(String dateString) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_BR);
    	return LocalDate.parse(dateString, formatter);
    }

    public static String convertLocalDateToString(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_BR);
        return localDate.format(formatter);
    }

    public static LocalDateTime convertStringToLocalDateTime(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    public static String convertLocalDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        return localDateTime.format(formatter);
    }
}
