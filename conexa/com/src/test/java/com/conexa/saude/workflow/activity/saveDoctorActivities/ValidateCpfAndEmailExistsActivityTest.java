package com.conexa.saude.workflow.activity.saveDoctorActivities;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conexa.saude.excepetions.BadRequestException;
import com.conexa.saude.model.dto.DoctorDTO;
import com.conexa.saude.model.entity.DoctorEntity;
import com.conexa.saude.repositories.DoctorRepository;

@ExtendWith(MockitoExtension.class)
public class ValidateCpfAndEmailExistsActivityTest {

    private static final String MOCK_CPF = "12345678912";
    
    private static final String MOCK_EMAIL = "teste@teste.com.br";

    @Mock
    private DoctorRepository repository;

    @InjectMocks
    private ValidateCpfAndEmailExistsActivity target; 

    @Test
    void NotExistsDoctorWithEmailAndCPFTest() {
        var doctorDTO = buildDoctorDTO(MOCK_CPF, MOCK_EMAIL);

        when(repository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.empty());
        when(repository.findByCpf(MOCK_CPF)).thenReturn(Optional.empty());

        var result = target.doExecute(doctorDTO);
       
        verify(repository, times(1)).findByCpf(MOCK_CPF);
        verify(repository, times(1)).findByEmail(MOCK_EMAIL);

        
        assertNull(result);
    }
    
    @Test
    void existsDoctorWithEmailAndCPFTest() {
        var doctor = buildDoctorEntity(MOCK_CPF, MOCK_EMAIL);
        var doctorDTO = buildDoctorDTO(MOCK_CPF, MOCK_EMAIL);

        when(repository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(doctor));
        
        assertThrows(BadRequestException.class, () -> target.doExecute(doctorDTO));
    }

    @Test
    void existsDoctorWithEmailTest() {
        var doctor = buildDoctorEntity(MOCK_CPF, MOCK_EMAIL);
        var doctorDTO = buildDoctorDTO(MOCK_CPF, MOCK_EMAIL);

        when(repository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.of(doctor));
        
        assertThrows(BadRequestException.class, () -> target.doExecute(doctorDTO));
    }

    @Test
    void existsDoctorWithCPFTest() {
        var doctor = buildDoctorEntity(MOCK_CPF, MOCK_EMAIL);
        var doctorDTO = buildDoctorDTO(MOCK_CPF, MOCK_EMAIL);

        when(repository.findByEmail(MOCK_EMAIL)).thenReturn(Optional.empty());
        when(repository.findByCpf(MOCK_CPF)).thenReturn(Optional.of(doctor));
        
        assertThrows(BadRequestException.class, () -> target.doExecute(doctorDTO));
    }
  
    private DoctorDTO buildDoctorDTO(String cpf, String email) {
        return DoctorDTO.builder()
        .cpf(cpf)
        .email(email)
        .build();
    } 

    private DoctorEntity buildDoctorEntity(String cpf, String email) {
        return DoctorEntity.builder()
        .cpf(cpf)
        .email(email)
        .build();
    } 

}
