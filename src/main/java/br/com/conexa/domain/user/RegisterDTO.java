package br.com.conexa.domain.user;

import br.com.conexa.utils.Telefone;
import br.com.conexa.utils.ValidPassword;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record RegisterDTO(
        @NotEmpty(message = "Email nao pode ser vazio")
        @NotBlank(message = "Email nao pode ser vazio")
        String email,
        @ValidPassword
        String senha,
        @ValidPassword
        String confirmacaoSenha,
        String especialidade,
        @CPF
        String cpf,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate dataNascimento,
        @Telefone
        String telefone) {
}
