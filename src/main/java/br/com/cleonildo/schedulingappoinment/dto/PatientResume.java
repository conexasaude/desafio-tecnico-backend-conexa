package br.com.cleonildo.schedulingappoinment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PatientResume(
        @JsonProperty("nome")
        String name,
        String cpf

) { }
