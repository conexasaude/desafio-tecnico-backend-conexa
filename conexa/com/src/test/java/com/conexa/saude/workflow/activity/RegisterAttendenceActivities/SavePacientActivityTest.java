package com.conexa.saude.workflow.activity.RegisterAttendenceActivities;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conexa.saude.model.dto.PacientDTO;
import com.conexa.saude.model.entity.PacienteEntity;
import com.conexa.saude.model.mapper.PacientMapper;
import com.conexa.saude.repositories.PatientRepository;

@ExtendWith(MockitoExtension.class)
public class SavePacientActivityTest {

    private static final String MOCK_CPF = "12345678912";

    @Mock
    private PacientMapper mapper;

    @Mock
    private PatientRepository repository;

    @InjectMocks
    private SavePacientActivity target; 

    @Test
    void pacienteExistTest() {
        var pacient = buildPacienteEntity();
        var pacientDTO = buildPacienteDTO();

        when(mapper.toPacient(buildPacienteDTO())).thenReturn(pacient);
        when(mapper.toPacientDTO(pacient)).thenReturn(pacientDTO);
        when(repository.findByCpf(MOCK_CPF)).thenReturn(Optional.of(pacient));

        target.doExecute(pacientDTO);

        verify(mapper, times(1)).toPacient(pacientDTO);
        verify(mapper, times(1)).toPacientDTO(pacient);
        verify(repository, times(1)).findByCpf(MOCK_CPF);
        verify(repository, times(0)).save(pacient);
    }
    
    @Test
    void pacienteNotExistTest() {
        var pacient = buildPacienteEntity();
        var pacientDTO = buildPacienteDTO();

        when(mapper.toPacient(buildPacienteDTO())).thenReturn(pacient);
        when(mapper.toPacientDTO(pacient)).thenReturn(pacientDTO);
        when(repository.findByCpf(MOCK_CPF)).thenReturn(Optional.empty());
        when(repository.save(pacient)).thenReturn(pacient);

        target.doExecute(pacientDTO);

        verify(mapper, times(1)).toPacient(pacientDTO); 
        verify(mapper, times(1)).toPacientDTO(pacient);
        verify(repository, times(1)).findByCpf(MOCK_CPF); 
        verify(repository, times(1)).save(pacient);
    }
  
    private PacientDTO buildPacienteDTO() {
        return PacientDTO.builder()
        .cpf(MOCK_CPF)
        .build();
    } 

    private PacienteEntity buildPacienteEntity() {
        return PacienteEntity.builder()
        .cpf(MOCK_CPF)
        .build();
    } 
}
