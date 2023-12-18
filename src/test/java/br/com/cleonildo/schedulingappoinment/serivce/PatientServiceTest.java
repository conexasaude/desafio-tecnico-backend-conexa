package br.com.cleonildo.schedulingappoinment.serivce;

import br.com.cleonildo.schedulingappoinment.dto.PatientRequest;
import br.com.cleonildo.schedulingappoinment.dto.PatientResponse;
import br.com.cleonildo.schedulingappoinment.entities.Patient;
import br.com.cleonildo.schedulingappoinment.exceptions.NotFoundException;
import br.com.cleonildo.schedulingappoinment.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository repository;
    private final ModelMapper mapper = new ModelMapper();
    private PatientService service;
    private static final Long ID = 1L;
    private static final String NAME = "Fernanda Margolo";
    private static final String CPF =  "944.480.250-71";
    private final Patient patient = new Patient(ID, NAME, CPF);
    private final PatientResponse response = new PatientResponse(ID, NAME, CPF);;

    @BeforeEach
    void setup() {
        this.service = new PatientService(repository, mapper);
    }

    @Test
    @DisplayName("Return the list of all patient when calling getAllPatients")
    void shouldReturnListPatients_WhenCallingGetAllPatients() {
        // Given
        given(repository.findAll()).willReturn(singletonList(patient));

        // Given
        var actual = this.service.getAllPatients();

        // Then
        assertThat(actual.isEmpty(), is(false));
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0), is(response));

        verify(this.repository).findAll();
        verifyNoMoreInteractions(this.repository);
    }


    @Test
    @DisplayName("Should get a patient by ID")
    void shouldGetPatient_WhenCallingGetPatientById_WhenIdExist() {
        // Given
        given(repository.findById(anyLong())).willReturn(Optional.of(patient));

        var actual = this.service.getPatientById(1L);

        // Then
        assertNotNull(actual);
        assertThat(actual, is(response));

        verify(repository).findById(anyLong());
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should throw NotFoundException when ID does not exist")
    void shouldThrowNotFoundException_WhenIdDoesNotExist() {
        // Given
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        var expectedMessage = "Patient not found!";

        // Then
        assertThatThrownBy(() -> service.getPatientById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    @DisplayName("Given valid patient request, it should save the patient")
    void shouldSavePatient_SuccessfullyWhenCallingSavePatient() {
        // Given
        given(repository.save(any(Patient.class))).willReturn(patient);

        // When
        var request = new PatientRequest(NAME, CPF);
        var actual = this.service.savePatient(request);

        // Then
        assertNotNull(actual);
        assertThat(actual, is(response));

        verify(repository).save(any(Patient.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should Update patient successfully when Id exist")
    void shouldUpdatePatient_SuccessfullyWhenCallingUpdatePatient_WhenIdExist() {
        var updateName = "Cleonildo Junior";
        var updateCPF = "093.947.630-40";
        var updatePatient = new Patient(ID, updateName, updateCPF);

        // Given
        given(repository.findById(anyLong())).willReturn(Optional.of(patient));
        given(repository.save(any(Patient.class))).willReturn(updatePatient);

        // When
        var patientRequest = new PatientRequest(updateName, updateCPF);
        var patientResponseUpdate = new PatientResponse(ID, updateName, updateCPF);
        var actual = this.service.updatePatient(ID, patientRequest);

        // Then
        assertNotNull(actual);
        assertThat(actual, is(patientResponseUpdate));

        verify(repository).save(any(Patient.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should throw NotFoundException when ID does not exist when calling updatePatient")
    void shouldThrowNotFoundException_WhenIdDoesNotExist_WhenCallingUpdatePatient() {
        // Given
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        var expectedMessage = "Patient not found!";
        var request = new PatientRequest(NAME, CPF);

        // Then
        assertThatThrownBy(() -> service.updatePatient(1L, request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    @DisplayName("Should delete a patient successsfully when calling NotFoundException when ID does not exist when calling updatePatient")
    void shouldDeletePatientSuccessfully_WhenCalliDeletePatientById_WhenIdExist() {
        // Given
        given(repository.findById(anyLong())).willReturn(Optional.of(patient));
        willDoNothing().given(repository).delete(any(Patient.class));

        this.service.deletePatientById(ID);

        // Then
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(patient);
        verifyNoMoreInteractions(repository);
    }


    @Test
    @DisplayName("Should throw NotFoundException when ID does not exist when calling deletePatientById")
    void shouldThrowNotFoundException_WhenIdDoesNotExist_WhenCallingDeletePatientById() {
        // Given
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        var expectedMessage = "Patient not found!";

        // Then
        assertThatThrownBy(() -> service.deletePatientById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(expectedMessage);
    }
}