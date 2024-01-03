package br.com.conexa.services;

import br.com.conexa.domain.attendance.Attendance;
import br.com.conexa.domain.attendance.AttendanceRequestDTO;
import br.com.conexa.repositories.AttendanceRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class AttendanceService {

    @Autowired
    AttendanceRepository attendanceRepository;

    public Optional<Attendance> newAttendance(@Valid AttendanceRequestDTO body) {

        Attendance newAttendance = new Attendance(body);

        return Optional.of(this.attendanceRepository.save(newAttendance));
    }
}
