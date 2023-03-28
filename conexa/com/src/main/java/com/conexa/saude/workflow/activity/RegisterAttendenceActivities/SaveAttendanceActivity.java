package com.conexa.saude.workflow.activity.registerAttendenceActivities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.saude.model.dto.AttendanceDTO;
import com.conexa.saude.model.entity.AttendanceEntity;
import com.conexa.saude.model.mapper.AttendanceMapper;
import com.conexa.saude.repositories.AttendanceReepository;
import com.conexa.saude.workflow.activity.generics.BaseActivity;

@Service
public class SaveAttendanceActivity implements BaseActivity<AttendanceDTO, AttendanceDTO> {

    @Autowired
    private AttendanceMapper mapper;

    @Autowired
    private AttendanceReepository repository;

    @Override
    public AttendanceDTO doExecute(AttendanceDTO attendanceDTO) {

        AttendanceEntity attendence = mapper.toAttendance(attendanceDTO);
        attendence.getPaciente().setId(attendanceDTO.getPaciente().getId());
        AttendanceEntity savedAttendance = repository.save(attendence);
        return mapper.toAttendanceDTO(savedAttendance);

    }

}
