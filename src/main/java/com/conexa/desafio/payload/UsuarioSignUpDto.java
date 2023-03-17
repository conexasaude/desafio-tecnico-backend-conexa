package com.conexa.desafio.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSignUpDto {

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

    @JsonFormat(pattern = "dd/MM/yyyy")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    private String telefone;
}
