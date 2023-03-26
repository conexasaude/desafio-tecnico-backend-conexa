package com.conexa.saude.workflow.activity.RegisterAttendenceActivities;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.saude.model.dto.PacientDTO;
import com.conexa.saude.model.entity.Paciente;
import com.conexa.saude.model.mapper.PacientMapper;
import com.conexa.saude.repositories.PatientRepository;
import com.conexa.saude.workflow.activity.generics.BaseActivity;

@Service
public class SavePacientActivity implements BaseActivity<PacientDTO, PacientDTO> {

    @Autowired
    private PacientMapper mapper;

    @Autowired
    private PatientRepository repository;

    @Override
    public PacientDTO doExecute(PacientDTO pacientDTO) { 

        Paciente paciente = mapper.toPacient(pacientDTO);  
        Optional<Paciente> existingPacient = repository.findByCpf(pacientDTO.getCpf());

        if(existingPacient.isPresent()){
            return mapper.toPacientDTO(existingPacient.get());
        }
        Paciente savedPacient = repository.save(paciente);

        return mapper.toPacientDTO(savedPacient);


    }


}
