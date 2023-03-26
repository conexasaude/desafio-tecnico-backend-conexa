package com.conexa.saude.workflow.activity.RegisterAttendenceActivities;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.saude.model.dto.AttendanceDTO;
import com.conexa.saude.model.entity.Attendance;
import com.conexa.saude.model.mapper.AttendanceMapper;
import com.conexa.saude.repositories.AttendanceReepository;
import com.conexa.saude.workflow.activity.generics.BaseActivity;
@Service
public class SaveAttendanceActivity implements BaseActivity<AttendanceDTO, AttendanceDTO>  {

    @Autowired
    private AttendanceMapper mapper;

    @Autowired
    private AttendanceReepository repository;

    @Override
    public AttendanceDTO doExecute(AttendanceDTO attendanceDTO) { 

        Attendance paciente = mapper.toAttendance(attendanceDTO);  
        Attendance savedAttendance = repository.save(paciente);

        return mapper.toAttendanceDTO(savedAttendance);
    }

    
}
