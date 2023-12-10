package com.felipe.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felipe.model.Attendance;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, UUID> {
}
