package br.com.cleonildo.schedulingappoinment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "nome", "cpf"})
public record PatientResponse(
        Long id,
        @JsonProperty("nome")
        String name,
        String cpf
) { }
