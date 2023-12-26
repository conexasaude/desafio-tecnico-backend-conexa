package br.com.cleonildo.schedulingappoinment.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record AttendanceResponse(
        @JsonProperty("dataHora")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
        Instant dateHour,

        @JsonProperty("paciente")
        PatientResume patient
) { }
