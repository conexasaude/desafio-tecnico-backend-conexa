package com.conexa.saude.model.dto;

import java.util.Date;

import com.conexa.saude.model.entity.DoctorEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
public class AttendanceDTO {

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date dataHora;

    private PacientDTO paciente;

    @JsonIgnore
    private String emailDoctor;
    
    @JsonIgnore
    private DoctorEntity doctor;
    
}

