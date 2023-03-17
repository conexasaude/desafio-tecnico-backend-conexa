package com.conexa.desafio.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotNull
    @NotEmpty
    @NotBlank
    private String email;


    @NotNull
    @NotEmpty
    @NotBlank
    private String senha;


    @NotNull
    @NotEmpty
    @NotBlank
    private String confirmacaoSenha;


    @NotNull
    @NotEmpty
    @NotBlank
    private String especialidade;

    @NotNull
    @NotEmpty
    @NotBlank
    private String cpf;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private String telefone;
}
