package com.conexa.desafio.payload;

import com.conexa.desafio.validators.Telefone;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupRequest {

    @Email(message = "{email.invalid}")
    @NotNull(message = "{email.not.null}")
    @NotEmpty(message = "{email.not.empty}")
    @NotBlank(message = "{email.not.blank}")
    private String email;


    @NotNull(message = "{senha.not.null}")
    @NotEmpty(message = "{senha.not.empty}")
    @NotBlank(message = "{senha.not.blank}")
    private String senha;


    @NotNull(message = "{senha.not.null}")
    @NotEmpty(message = "{senha.not.empty}")
    @NotBlank(message = "{senha.not.blank}")
    private String confirmacaoSenha;


    @NotNull(message = "{especialidade.not.null}")
    @NotEmpty(message = "{especialidade.not.empty}")
    @NotBlank(message = "{especialidade.not.blank}")
    private String especialidade;

    @CPF(message = "{cpf.invalid}")
    @NotNull(message = "{cpf.not.null}")
    @NotEmpty(message = "{cpf.not.empty}")
    @NotBlank(message = "{cpf.not.blank}")
    private String cpf;

    @Past(message = "{dataNascimento.invalid}")
    @NotNull(message = "{dataNascimento.not.null}")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Telefone
    @NotNull(message = "{telefone.not.null}")
    @NotEmpty(message = "{telefone.not.empty}")
    @NotBlank(message = "{telefone.not.blank}")
    private String telefone;
}
