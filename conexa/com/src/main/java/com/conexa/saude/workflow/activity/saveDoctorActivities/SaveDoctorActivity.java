package com.conexa.saude.workflow.activity.saveDoctorActivities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.saude.model.dto.DoctorDTO;
import com.conexa.saude.model.dto.DoctorMinimalDTO;
import com.conexa.saude.model.entity.Doctor;
import com.conexa.saude.model.mapper.DoctorMapper;
import com.conexa.saude.repositories.DoctorRepository;
import com.conexa.saude.workflow.activity.generics.BaseActivity;
import com.conexa.saude.workflow.activity.generics.EncriptPasswordActivity;

@Service
public class SaveDoctorActivity implements BaseActivity<DoctorDTO, DoctorMinimalDTO> {

    @Autowired
    private DoctorMapper mapper;

    @Autowired
    private DoctorRepository doctorRepository;
    
	@Autowired
	private EncriptPasswordActivity encode;

    @Override
    public DoctorMinimalDTO doExecute(DoctorDTO doctorDTO)  {
        Doctor doctor = mapper.toMedico(doctorDTO);        
        var encodetPassword = encode.doExecute(doctor.getSenha());

        doctor.setSenha(encodetPassword);

        Doctor saveDoctor = doctorRepository.save(doctor);

        return mapper.toDoctorMinimal(saveDoctor);
    }
    
}
