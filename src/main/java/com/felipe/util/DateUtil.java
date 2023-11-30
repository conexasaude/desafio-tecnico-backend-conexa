package com.felipe.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final String DATE_PATTERN_BR = "dd/MM/yyyy";

    public static LocalDate convertStringToLocalDate(String dateString) {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_PATTERN_BR);
    	return LocalDate.parse(dateString, formatter);
    }
}
