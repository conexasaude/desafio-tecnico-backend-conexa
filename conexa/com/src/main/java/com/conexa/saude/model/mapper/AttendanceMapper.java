package com.conexa.saude.model.mapper;

import org.mapstruct.Mapper;

import com.conexa.saude.model.dto.AttendanceDTO;
import com.conexa.saude.model.entity.Attendance;


@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    Attendance toAttendance(AttendanceDTO attendanceDTO); 

    AttendanceDTO toAttendanceDTO(Attendance attendance);
    
}
