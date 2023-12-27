package br.com.cleonildo.schedulingappoinment.repository;

import br.com.cleonildo.schedulingappoinment.entities.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
}