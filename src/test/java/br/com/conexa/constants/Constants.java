package br.com.conexa.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Constants {

    //Login
    public static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJjb25leGEtc2F1ZGUiLCJzdWIiOiJtZWRpY29AZW1haWwuY29tIiwiZXhwIjoxNzA0MjM4NDU5fQ.a_sOkNIFKwNwnvup3gpLN0QLABlTUrMSlCQE_iwsup4";
    public static final String EMAIL = "medico@email.com";
    public static final String SENHA = "senh@Medico123";
    public static final String ESPECIALIDADE = "Cardiologista";
    public static final String CPF = "752.318.330-10";
    public static final LocalDate DATA_NASCIMENTO = LocalDate.parse("10/03/1980", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    public static final String TELEFONE = "(21) 93232-6565";

    //Paciente
    public static final String NOME_PACIENTE = "João Castro";
    public static final String CPF_PACIENTE = "752.318.330-10";
    public static final LocalDateTime DATA_NASCIMENTO_PACIENTE = LocalDateTime.parse("2020-08-03 09:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

}
