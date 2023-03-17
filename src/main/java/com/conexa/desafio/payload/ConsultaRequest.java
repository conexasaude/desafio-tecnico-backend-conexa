package com.conexa.desafio.payload;

import com.conexa.desafio.models.ConsultaEntity;
import com.conexa.desafio.models.PacienteEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsultaRequest {

  @JsonProperty("dataHora")
  @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
  private LocalDateTime dataHora;

  @JsonProperty("paciente")
  private Paciente paciente;

  @Data
  @Builder
  public static class Paciente {
    @JsonProperty("nome")
    private String nome;

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
