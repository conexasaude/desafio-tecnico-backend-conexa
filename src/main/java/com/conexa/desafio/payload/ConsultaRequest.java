package com.conexa.desafio.payload;

import com.conexa.desafio.models.ConsultaEntity;
import com.conexa.desafio.models.PacienteEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaRequest {

  @Future(message = "{data.consulta.invalid}")
  @JsonProperty("dataHora")
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dataHora;

  @JsonProperty("paciente")
  @Valid
  @NotNull(message = "{paciente.not.null}")
  private Paciente paciente;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Paciente {
    @NotNull(message = "{nome.not.null}")
    @NotEmpty(message = "{nome.not.empty}")
    @NotBlank(message = "{nome.not.blank}")
    @JsonProperty("nome")
    private String nome;

    @CPF(message = "{cpf.invalid}")
    @NotNull(message = "{cpf.not.null}")
    @NotEmpty(message = "{cpf.not.empty}")
    @NotBlank(message = "{cpf.not.blank}")
    @JsonProperty("cpf")
    private String cpf;
  }

  public ConsultaEntity convertToEntity() {
    return ConsultaEntity.builder()
        .dataHora(this.getDataHora())
        .paciente(
            PacienteEntity.builder()
                .nome(this.getPaciente().getNome())
                .cpf(this.getPaciente().getCpf())
                .build())
        .build();
  }
}
