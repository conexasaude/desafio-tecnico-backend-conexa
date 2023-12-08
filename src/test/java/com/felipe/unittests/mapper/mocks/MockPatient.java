package com.felipe.unittests.mapper.mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.felipe.model.Patient;
import com.felipe.model.dto.v1.PatientDTO;
import com.felipe.utils.documents.GenerateDocument;
import com.github.javafaker.Faker;

public class MockPatient {

	private GenerateDocument generateDocument = new GenerateDocument();

	private Faker faker = new Faker();

	public Patient mockEntity() {
		return mockEntity(0);
	}

	public PatientDTO mockDTO() {
		return mockDTO(0);
	}

	public List<Patient> mockEntityList() {
		List<Patient> patients = new ArrayList<Patient>();
		for (int i = 0; i < 8; i++) {
			patients.add(mockEntity(i));
		}
		return patients;
	}

	public List<PatientDTO> mockDTOList() {
		List<PatientDTO> patients = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			patients.add(mockDTO(i));
		}
		return patients;
	}

	public List<Patient> mockRandomEntityList(int quantityPatients, boolean withId) {
		List<Patient> patients = new ArrayList<Patient>();
		for (int i = 0; i < quantityPatients; i++) {
			patients.add(mockRandomEntity(withId));
		}
		return patients;
	}

	public Patient mockEntity(Integer number) {
		Patient patient = new Patient();
//        patient.setId(UUID.fromString("0c38ae25-0d29-43ad-91d4-73e91309267b"));
		patient.setFullName("Full Name Test" + number);
		patient.setCpf("100.200.300-4" + number);
		patient.setHealthInsurance("SulAmericano" + number);
		return patient;
	}

	public PatientDTO mockDTO(Integer number) {
		PatientDTO patient = new PatientDTO();
//        patient.setKey(UUID.fromString("1bf6842a-06f6-480d-b766-6e2725e86007"));
		patient.setFullName("Full Name Test" + number);
		patient.setCpf("100.200.300-4" + number);
		patient.setHealthInsurance("SulAmericano" + number);
		return patient;
	}

	public Patient mockRandomEntity(boolean withId) {
		UUID id = withId ? UUID.randomUUID() : null;
		Patient entity = new Patient();
		entity.setId(id);
		entity.setFullName(faker.name().fullName());
		entity.setCpf(generateDocument.cpf(true));
		entity.setHealthInsurance("SulAmericano");
		return entity;
	}

}
