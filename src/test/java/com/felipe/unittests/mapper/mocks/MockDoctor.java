package com.felipe.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.felipe.model.Doctor;
import com.felipe.model.dto.v1.DoctorDTO;
import com.felipe.util.DateUtil;
import com.felipe.utils.EmailGeneretor;
import com.felipe.utils.documents.GenerateDocument;
import com.github.javafaker.Faker;

public class MockDoctor {

	private GenerateDocument generateDocument = new GenerateDocument();

	private Faker faker = new Faker();

	public Doctor mockEntity() {
		return mockEntity(0);
	}

	public DoctorDTO mockDTO() {
		return mockDTO(0);
	}

	public List<Doctor> mockEntityList() {
		List<Doctor> listEntity = new ArrayList<Doctor>();
		for (int i = 0; i < 8; i++) {
			listEntity.add(mockEntity(i));
		}
		return listEntity;
	}

	public List<DoctorDTO> mockDTOList() {
		List<DoctorDTO> listDto = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			listDto.add(mockDTO(i));
		}
		return listDto;
	}

	public List<Doctor> mockRandomEntityList(int quantity, boolean withId) {
		List<Doctor> listEntity = new ArrayList<Doctor>();
		for (int i = 0; i < quantity; i++) {
			listEntity.add(mockRandomEntity(withId));
		}
		return listEntity;
	}
	
	public List<DoctorDTO> mockRandomDTOList(int quantity, boolean withId) {
		List<DoctorDTO> listDto = new ArrayList<DoctorDTO>();
		for (int i = 0; i < quantity; i++) {
			listDto.add(mockRandomDTO(withId));
		}
		return listDto;
	}

	public Doctor mockEntity(Integer number) {
		Doctor entity = new Doctor();
//        doctor.setId(UUID.fromString("0c38ae25-0d29-43ad-91d4-73e91309267b"));
		entity.setEmail("doctor.email" + number + "@gmail.com");
		entity.setFullName("Full Name Test" + number);
		entity.setCpf("100.200.300-4" + number);
		entity.setBirthDate(DateUtil.convertStringToLocalDate("21/05/198" + number));
		entity.setPhone("(21) 3232-656" + number);
		entity.setSpecialty("Pediatra" + number);
		return entity;
	}

	public DoctorDTO mockDTO(Integer number) {
		DoctorDTO entity = new DoctorDTO();
//        doctor.setKey(UUID.fromString("1bf6842a-06f6-480d-b766-6e2725e86007"));
		entity.setEmail("doctor.email" + number + "@gmail.com");
		entity.setFullName("Full Name Test" + number);
		entity.setCpf("100.200.300-4" + number);
		entity.setBirthDate("21/05/198" + number);
		entity.setPhone("(21) 3232-656" + number);
		entity.setSpecialty("Pediatra" + number);
		return entity;
	}

	public Doctor mockRandomEntity(boolean withId) {
		UUID id = withId ? UUID.randomUUID() : null;
		String fullname = faker.name().fullName();
		String email = EmailGeneretor.generateEmail(fullname);
		String cpf = generateDocument.cpf(true);
		String phone = faker.phoneNumber().phoneNumber();
		return new Doctor(id, email, fullname, cpf, DateUtil.convertStringToLocalDate("21/05/1981"), phone,
				"Pediatra");
	}
	
	public DoctorDTO mockRandomDTO(boolean withId) {
		UUID id = withId ? UUID.randomUUID() : null;
		String fullname = faker.name().fullName();
		String email = EmailGeneretor.generateEmail(fullname);
		String cpf = generateDocument.cpf(true);
		String phone = faker.phoneNumber().phoneNumber();
		return new DoctorDTO(id, email, fullname, cpf, "21/05/1981", phone,
				"Pediatra");
	}

}
