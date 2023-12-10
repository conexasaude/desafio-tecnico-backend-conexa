package com.felipe.util;

public class StringUtil {
    public static String removeNonNumeric(String input) {
        return input.replaceAll("[^0-9]", "");
    }
    
}
