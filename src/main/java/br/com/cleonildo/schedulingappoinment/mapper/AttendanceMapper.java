package br.com.cleonildo.schedulingappoinment.mapper;

import br.com.cleonildo.schedulingappoinment.dto.AttendanceResponse;
import br.com.cleonildo.schedulingappoinment.dto.PatientResponse;
import br.com.cleonildo.schedulingappoinment.dto.PatientResume;
import br.com.cleonildo.schedulingappoinment.entities.Attendance;
import org.springframework.stereotype.Service;

@Service
public class AttendanceMapper implements Mapper<Attendance, AttendanceResponse> {


    @Override
    public AttendanceResponse apply(Attendance attendance) {
        return new AttendanceResponse(
                attendance.getDateHour(),
                new PatientResume(
                        attendance.getPatient().getName(),
                        attendance.getPatient().getCpf()
                )
        );
    }
}
