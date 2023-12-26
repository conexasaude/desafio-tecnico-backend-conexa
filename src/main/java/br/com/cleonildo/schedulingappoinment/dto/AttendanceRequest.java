package br.com.cleonildo.schedulingappoinment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;

import java.time.Instant;

public record AttendanceRequest(
        @JsonProperty("dataHora")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        @FutureOrPresent
        Instant dateHour,
        @JsonProperty("paciente_id")
        @Positive
        Long patientId
) { }
