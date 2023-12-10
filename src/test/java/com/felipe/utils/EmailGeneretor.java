package com.felipe.utils;

public class EmailGeneretor {
    public static String generateEmail(String fullName) {
        // Converte o nome para minúsculas e remove espaços em branco
        String cleanedName = fullName.toLowerCase().replaceAll("\\s", "");

        // Gera um sufixo de e-mail aleatório
        String randomSuffix = generateRandomSuffix();

        // Monta o endereço de e-mail
        String email = cleanedName + "." + randomSuffix + "@example.com";

        return email;
    }

    private static String generateRandomSuffix() {
        // Aqui você pode implementar a lógica para gerar um sufixo aleatório, por exemplo, usando números e letras
        // Neste exemplo simples, estamos usando apenas um número aleatório entre 1000 e 9999
        int randomNum = 1000 + (int) (Math.random() * 9000);
        return String.valueOf(randomNum);
    }
}
