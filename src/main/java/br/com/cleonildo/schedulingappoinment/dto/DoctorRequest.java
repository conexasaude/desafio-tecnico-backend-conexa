package br.com.cleonildo.schedulingappoinment.dto;

import br.com.cleonildo.schedulingappoinment.enums.Specialty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record DoctorRequest(
        @Email(message = "Email fora do padão")
        String email,

        @NotBlank(message = "Senha não pode estar branca")
        @NotEmpty(message = "Senha não pode estar vazia")
        String password,

        @NotBlank(message = "Confirmar senha não pode estar branca")
        @NotEmpty(message = "Confirmar senha não pode estar vazia")
        String confirmPassword,

        @NotNull(message = "Especialidade não pode ser nula")
        Specialty specialty,

        @CPF(message = "CPF não está no padrão valido")
        String cpf,

        @NotNull(message = "Data de nascimento não pode ser nula")
        @PastOrPresent(message = "Data de nascimento não pode ser futura")
        LocalDate birthdate,

        @NotBlank(message = "Telefone não pode estar em branco")
        @NotEmpty(message = "Telefone não pode estar vazio")
        String phone

){ }