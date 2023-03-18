package com.conexa.desafio.payload;

import com.conexa.desafio.models.ConsultaEntity;
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
public class ConsultaResponse {

    @JsonProperty("dataConsulta")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHora;

    @JsonProperty("paciente")
    private Paciente paciente;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Paciente{

        @JsonProperty("nome")
        private String nome;

    }

    public static ConsultaResponse convertoToResponse(ConsultaEntity entity){
    return ConsultaResponse.builder()
        .dataHora(entity.getDataHora())
        .paciente(Paciente.builder()
                .nome(entity.getPaciente().getNome())
                .build())
        .build();
    }
}
