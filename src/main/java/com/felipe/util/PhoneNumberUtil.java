package com.felipe.util;

public class PhoneNumberUtil {
    public static String unformatPhoneNumber(String phoneNumber) {
        // Remove caracteres não numéricos
        String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");

        // Lógica de formatação do número
        if (cleanedNumber.length() == 11) {
            // (XX) XXXXX-XXXX para 11XXXXXXXXX
            return cleanedNumber;
        } else if (cleanedNumber.length() == 10) {
            // (XX) XXXX-XXXX para 11XXXXXXXX
            return "11" + cleanedNumber;
        } else {
            // Retorne como está se não corresponder aos formatos esperados
            return phoneNumber;
        }
    }

    public static String formatPhoneNumber(String phoneNumber) {
        // Remove caracteres não numéricos
        String cleanedNumber = phoneNumber.replaceAll("[^0-9]", "");

        // Lógica de desformatação do número
        if (cleanedNumber.length() == 11) {
            // 11XXXXXXXXX para (XX) XXXXX-XXXX
            return "(" + cleanedNumber.substring(0, 2) + ") " +
                   cleanedNumber.substring(2, 7) + "-" +
                   cleanedNumber.substring(7);
        } else if (cleanedNumber.length() == 10) {
            // 11XXXXXXXX para (XX) XXXX-XXXX
            return "(" + cleanedNumber.substring(0, 2) + ") " +
                   cleanedNumber.substring(2, 6) + "-" +
                   cleanedNumber.substring(6);
        } else {
            // Retorne como está se não corresponder aos formatos esperados
            return phoneNumber;
        }
    }
}
