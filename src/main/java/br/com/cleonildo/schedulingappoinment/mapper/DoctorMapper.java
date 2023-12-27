package br.com.cleonildo.schedulingappoinment.mapper;

import br.com.cleonildo.schedulingappoinment.dto.DoctorResponse;
import br.com.cleonildo.schedulingappoinment.entities.Doctor;
import org.springframework.stereotype.Service;

@Service
public class DoctorMapper implements Mapper<Doctor, DoctorResponse> {

    @Override
    public DoctorResponse apply(Doctor doctor) {
        return new DoctorResponse(
                doctor.getId(),
                doctor.getEmail(),
                doctor.getSpecialty().getDescription(),
                doctor.getCpf(),
                doctor.getBirthdate(),
                doctor.getPhone()
        );
    }
}
