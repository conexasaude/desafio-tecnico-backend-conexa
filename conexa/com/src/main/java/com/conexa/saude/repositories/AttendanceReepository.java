package com.conexa.saude.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.conexa.saude.model.dto.AttendanceDTO;
import com.conexa.saude.model.entity.AttendanceEntity;

@Repository
public interface AttendanceReepository extends JpaRepository<AttendanceEntity, String> {

    Optional<AttendanceEntity> findByPaciente(AttendanceDTO attendanceDTO);

}
