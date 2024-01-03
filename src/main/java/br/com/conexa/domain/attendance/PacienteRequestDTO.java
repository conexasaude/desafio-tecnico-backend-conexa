package br.com.conexa.domain.attendance;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record PacienteRequestDTO(
        @NotEmpty(message = "Nome nao pode ser vazio")
        @NotBlank(message = "Nome nao pode ser vazio")
        String nome,
        @NotNull(message = "cpf nao pode ser vazio")
        @CPF
        String cpf
) {
}
