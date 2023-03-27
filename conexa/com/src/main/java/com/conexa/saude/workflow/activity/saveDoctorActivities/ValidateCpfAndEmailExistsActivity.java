package com.conexa.saude.workflow.activity.saveDoctorActivities;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.saude.excepetions.BadRequestException;
import com.conexa.saude.model.dto.DoctorDTO;
import com.conexa.saude.model.entity.DoctorEntity;
import com.conexa.saude.repositories.DoctorRepository;
import com.conexa.saude.workflow.activity.generics.BaseActivity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateCpfAndEmailExistsActivity implements BaseActivity<DoctorDTO, Void>{

    @Autowired
    private DoctorRepository repository;

    public Void doExecute(DoctorDTO doctorDTO) {
        Optional<DoctorEntity> doctorBymail = repository.findByEmail(doctorDTO.getEmail());

        if(doctorBymail.isPresent()){
            throw new BadRequestException("Email ja cadastrado.");
        }

        Optional<DoctorEntity> doctorByCpf = repository.findByCpf(doctorDTO.getCpf());

        if(doctorByCpf.isPresent()){
            throw new BadRequestException("Cpf ja cadastrado.");
        }

        return null;
         
    }

}

