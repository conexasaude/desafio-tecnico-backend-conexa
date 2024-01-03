package br.com.conexa.utils;

import java.util.regex.*;

public class MainTestesUtils {

    public static boolean validarTelefone(String telefone) {
        // Defina a expressão regular para os dois padrões aceitáveis
        //String regex = "\\((\\d{2})\\)\\s((3\\d{3}-\\d{4})|(9\\d{4}-\\d{4}))";
        String regex = "\\((\\d{2})\\)\\s?((3\\d{3}-\\d{4})|(9\\d{4}-\\d{4}))";

        // Crie o padrão e o matcher
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(telefone);

        // Verifique se há correspondência
        return matcher.matches();
    }

    public static void main(String[] args) {
        // Exemplos de uso
        String telefone1 = "(21)232-6565";
        String telefone2 = "(21) 3232-6565";
        String telefone3 = "(21) 99913-4567";

        if (validarTelefone(telefone1)) {
            System.out.println("Telefone 1 válido!");
        } else {
            System.out.println("Telefone 1 inválido!");
        }

        if (validarTelefone(telefone2)) {
            System.out.println("Telefone 2 válido!");
        } else {
            System.out.println("Telefone 2 inválido!");
        }

        if (validarTelefone(telefone3)) {
            System.out.println("Telefone 3 válido!");
        } else {
            System.out.println("Telefone 3 inválido!");
        }
    }
}