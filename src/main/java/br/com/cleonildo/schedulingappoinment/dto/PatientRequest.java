package br.com.cleonildo.schedulingappoinment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

public record PatientRequest(
        @NotEmpty(message = "Nome não pode estar vazio")
        @NotBlank(message = "Nome não pode estar em branco")
        @JsonProperty("nome")
        String name,
        @CPF(message = "CPF não está no padrão valido")
        String cpf
) { }
