package br.com.cleonildo.schedulingappoinment.dto;

import br.com.cleonildo.schedulingappoinment.entities.Doctor;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

@JsonPropertyOrder(value = {"id", "email", "especialidade", "dataNascimento", "cpf"})
public record DoctorResponse(
        Long id,
        String email,
        @JsonProperty("especialidade")
        String specialty,
        String cpf,
        @JsonProperty("dataNascimento")
        LocalDate birthdate,
        @JsonProperty("telefone")
        String phone
) { }
