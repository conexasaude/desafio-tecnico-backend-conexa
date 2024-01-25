package com.felipe.unittests.mapper.mocks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.felipe.model.Attendance;
import com.felipe.model.Patient;
import com.felipe.model.dto.v1.AttendanceDTO;
import com.felipe.model.dto.v1.PatientDTO;
import com.felipe.util.DateUtil;
import com.felipe.utils.documents.GenerateDocument;
import com.github.javafaker.Faker;

public class MockAttendance {

	private GenerateDocument generateDocument = new GenerateDocument();
	private MockPatient inputObject = new MockPatient();

	private Faker faker = new Faker();

	public Attendance mockEntity(LocalDateTime dateTime) {
		return mockEntity(0, dateTime);
	}

	public AttendanceDTO mockDTO() {
		return mockDTO(0);
	}

	public List<Attendance> mockEntityList(LocalDateTime dateTime) {
		List<Attendance> attendances = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			attendances.add(mockEntity(i, dateTime));
		}
		return attendances;
	}

	public List<AttendanceDTO> mockDTOList() {
		List<AttendanceDTO> attendances = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			attendances.add(mockDTO(i));
		}
		return attendances;
	}

	public List<Attendance> mockRandomEntityList(int quantityAttendances, boolean withId) {
		List<Attendance> attendances = new ArrayList<>();
		for (int i = 0; i < quantityAttendances; i++) {
			attendances.add(mockRandomEntity(withId));
		}
		return attendances;
	}

	public Attendance mockEntity(Integer number, LocalDateTime dateTime) {
		Attendance attendance = new Attendance();
		attendance.setDateTime(dateTime);
		Patient patient = inputObject.mockEntity(number);
		attendance.setPatient(patient);
		return attendance;
	}

	public AttendanceDTO mockDTO(Integer number) {
		AttendanceDTO attendance = new AttendanceDTO();
		attendance.setDateTime(DateUtil.convertLocalDateTimeToString(LocalDateTime.now().plusHours(7)));
		PatientDTO patient = inputObject.mockDTO(number);
		attendance.setPatient(patient);
		return attendance;
	}

	public Attendance mockRandomEntity(boolean withId) {
		UUID id = withId ? UUID.randomUUID() : null;
		Attendance entity = new Attendance();
		entity.setId(id);
		entity.setDateTime(LocalDateTime.now().plusHours(7));
		Patient patient = inputObject.mockRandomEntity(false);
		entity.setPatient(patient);
		return entity;
	}

}
