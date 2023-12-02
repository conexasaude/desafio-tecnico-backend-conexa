package com.felipe.utils;

import java.text.MessageFormat;
import java.util.Random;

public class CPFGeneretor {

	public static void main(String[] args) {
        String cpf = generateCPF();
        System.out.println("CPF gerado: " + cpf);
    }

    public static String generateCPF() {
        int[] cpfArray = new int[11];

        // Gera os primeiros 9 dígitos aleatórios
        Random random = new Random();
        for (int i = 0; i < 9; i++) {
            cpfArray[i] = random.nextInt(10);
        }

        // Calcula o primeiro dígito verificador
        cpfArray[9] = calculateDigit(cpfArray, 10);

        // Calcula o segundo dígito verificador
        cpfArray[10] = calculateDigit(cpfArray, 11);

        // Converte o array para uma string formatada
        StringBuilder cpfBuilder = new StringBuilder();
        for (int digit : cpfArray) {
            cpfBuilder.append(digit);
        }

        return formatCPF(cpfArray);
    }

    private static int calculateDigit(int[] cpfArray, int length) {
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += cpfArray[i] * (length + 1 - i);
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : (11 - remainder);
    }
    
    private static String formatCPF(int[] cpfArray) {
        String cpfPattern = "{0}{1}{2}.{3}{4}{5}.{6}{7}{8}-{9}{10}";
        return MessageFormat.format(cpfPattern, cpfArray[0], cpfArray[1], cpfArray[2],
                cpfArray[3], cpfArray[4], cpfArray[5], cpfArray[6], cpfArray[7],
                cpfArray[8], cpfArray[9], cpfArray[10]);
    }
}
