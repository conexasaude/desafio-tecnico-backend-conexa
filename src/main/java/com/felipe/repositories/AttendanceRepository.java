package com.felipe.repositories;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felipe.model.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
    Page<Attendance> findByDateTimeBetween(LocalDateTime initialDate, LocalDateTime endDate, Pageable pageable);

}
