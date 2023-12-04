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
        List<Doctor> doctors = new ArrayList<Doctor>();
        for (int i = 0; i < 8; i++) {
        	doctors.add(mockEntity(i));
        }
        return doctors;
    }

    public List<DoctorDTO> mockDTOList() {
        List<DoctorDTO> doctors = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
        	doctors.add(mockDTO(i));
        }
        return doctors;
    }
	 
    public List<Doctor> mockRandomEntityList(int quantityDoctors, boolean withId) {
        List<Doctor> doctors = new ArrayList<Doctor>();
        for (int i = 0; i < quantityDoctors; i++) {
        	doctors.add(mockRandomEntity(withId));
        }
        return doctors;
    }
    
    public Doctor mockEntity(Integer number) {
        Doctor doctor = new Doctor();
//        doctor.setId(UUID.fromString("0c38ae25-0d29-43ad-91d4-73e91309267b"));
        doctor.setEmail("doctor.email" + number + "@gmail.com");
        doctor.setFullName("Full Name Test" + number);
        doctor.setPassword("password01" + number);
        doctor.setCpf("100.200.300-4" + number);
        doctor.setBirthDate(DateUtil.convertStringToLocalDate("21/05/198" + number));
        doctor.setPhone("(21) 3232-656" + number);
        doctor.setSpecialty("Pediatra" + number);
        doctor.setMedicalLicense("ZA-4922-F" + number);        return doctor;
    }

    public DoctorDTO mockDTO(Integer number) {
        DoctorDTO doctor = new DoctorDTO();
//        doctor.setKey(UUID.fromString("1bf6842a-06f6-480d-b766-6e2725e86007"));
        doctor.setEmail("doctor.email" + number + "@gmail.com");
        doctor.setFullName("Full Name Test" + number);
        doctor.setPassword("password01" + number);
        doctor.setCpf("100.200.300-4" + number);
        doctor.setBirthDate(DateUtil.convertStringToLocalDate("21/05/198" + number));
        doctor.setPhone("(21) 3232-656" + number);
        doctor.setSpecialty("Pediatra" + number);
        doctor.setMedicalLicense("ZA-4922-F" + number);
        return doctor;
    }
    
    public Doctor mockRandomEntity(boolean withId) {
    	UUID id = withId ? UUID.randomUUID() : null;
		String fullname = faker.name().fullName();
		String email = EmailGeneretor.generateEmail(fullname);
		String cpf = generateDocument.cpf(true);
		String phone = faker.phoneNumber().phoneNumber();
		return new Doctor(id, email, fullname, "senha123", cpf,
				DateUtil.convertStringToLocalDate("21/05/1981"), phone, "Pediatra", "ZA-4922-FA");
    }
    
}
