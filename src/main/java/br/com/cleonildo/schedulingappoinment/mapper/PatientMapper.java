package br.com.cleonildo.schedulingappoinment.mapper;

import br.com.cleonildo.schedulingappoinment.dto.PatientResponse;
import br.com.cleonildo.schedulingappoinment.entities.Patient;
import org.springframework.stereotype.Service;

@Service
public class PatientMapper implements Mapper<Patient, PatientResponse> {

    @Override
    public PatientResponse apply(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getName(),
                patient.getCpf()
        );
    }
}
