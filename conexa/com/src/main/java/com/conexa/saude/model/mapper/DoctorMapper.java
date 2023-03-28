package com.conexa.saude.model.mapper;

import org.mapstruct.Mapper;

import com.conexa.saude.model.dto.DoctorDTO;
import com.conexa.saude.model.dto.DoctorMinimalDTO;
import com.conexa.saude.model.dto.LoginDTO;
import com.conexa.saude.model.entity.DoctorEntity;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    DoctorEntity toMedico(DoctorDTO doctorDTO);

    DoctorEntity toLoginMedico(LoginDTO loginDTO);

    DoctorMinimalDTO toDoctorMinimal(DoctorEntity doctor);

}
