package com.conexa.saude.model.mapper;

import org.mapstruct.Mapper;

import com.conexa.saude.model.dto.AttendanceDTO;
import com.conexa.saude.model.entity.AttendanceEntity;

@Mapper(componentModel = "spring")
public interface AttendanceMapper {

    AttendanceEntity toAttendance(AttendanceDTO attendanceDTO);

    AttendanceDTO toAttendanceDTO(AttendanceEntity attendance);

}
