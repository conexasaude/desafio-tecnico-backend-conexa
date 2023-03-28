package com.conexa.saude.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.saude.excepetions.NotFoundException;
import com.conexa.saude.model.dto.AttendanceDTO;
import com.conexa.saude.model.dto.PacientDTO;
import com.conexa.saude.model.entity.DoctorEntity;
import com.conexa.saude.repositories.DoctorRepository;
import com.conexa.saude.workflow.activity.generics.BaseActivity;
import com.conexa.saude.workflow.activity.generics.ValidateCpfActivity;
import com.conexa.saude.workflow.activity.registerAttendenceActivities.SaveAttendanceActivity;
import com.conexa.saude.workflow.activity.registerAttendenceActivities.SavePacientActivity;
import com.conexa.saude.workflow.activity.registerAttendenceActivities.ValidateDateTimeActivity;
import com.conexa.saude.workflow.activity.saveDoctorActivities.NormalizeCpfActivity;

@Service
public class RegisterAttendanceWorkFlow implements BaseActivity<AttendanceDTO, Void> {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private NormalizeCpfActivity normalizeCpf;

    @Autowired
    private ValidateCpfActivity validateCpfActivity;

    @Autowired
    private ValidateDateTimeActivity validateDateTimeActivity;

    @Autowired
    private SavePacientActivity savePacientActivity;

    @Autowired
    private SaveAttendanceActivity saveAttendanceActivity;

    @Override
    public Void doExecute(AttendanceDTO attendanceDTO) {
        var pacienteCpf = attendanceDTO.getPaciente();
        var normalizedCpf = normalizeCpf.doExecute(attendanceDTO.getPaciente().getCpf());
        
        pacienteCpf.setCpf(normalizedCpf);
        attendanceDTO.setPaciente(pacienteCpf);

        validateCpfActivity.doExecute(attendanceDTO.getPaciente().getCpf());

        validateDateTimeActivity.doExecute(attendanceDTO);

        PacientDTO paciente = savePacientActivity.doExecute(attendanceDTO.getPaciente());

        attendanceDTO.setPaciente(paciente);

        DoctorEntity doctor = doctorRepository.findByEmail(attendanceDTO.getEmailDoctor())
                .orElseThrow(() -> new NotFoundException("Doutor nao encontrado."));

        attendanceDTO.setDoctor(doctor);
        saveAttendanceActivity.doExecute(attendanceDTO);

        return null;

    }

}
