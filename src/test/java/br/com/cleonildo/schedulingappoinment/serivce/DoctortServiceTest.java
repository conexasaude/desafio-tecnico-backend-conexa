package br.com.cleonildo.schedulingappoinment.serivce;

import br.com.cleonildo.schedulingappoinment.dto.DoctorRequest;
import br.com.cleonildo.schedulingappoinment.dto.DoctorRequestNoPassword;
import br.com.cleonildo.schedulingappoinment.dto.DoctorResponse;
import br.com.cleonildo.schedulingappoinment.entities.Doctor;
import br.com.cleonildo.schedulingappoinment.enums.Specialty;
import br.com.cleonildo.schedulingappoinment.exceptions.NotFoundException;
import br.com.cleonildo.schedulingappoinment.exceptions.PasswordDoesNotMatch;
import br.com.cleonildo.schedulingappoinment.mapper.DoctorMapper;
import br.com.cleonildo.schedulingappoinment.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
class DoctortServiceTest {

    @Mock
    private DoctorRepository repository;
    private final DoctorMapper mapper = new DoctorMapper();
    private DoctortService service;
    private static final Long ID = 1L;
    private static final String EMAIL = "doutorwulices@hotmail.com";
    private static final String PASSWORD = "12345";
    private static final String COMFIRM_PASSWORD = "12345";
    private static final Specialty SPECIALTY = Specialty.NEUROLOGIST;
    private static final String CPF = "722.110.540-69";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1989,12, 28);
    private static final String PHONE = "(21) 97981-6155";
    private final Doctor doctor = new Doctor(ID, EMAIL, PASSWORD, SPECIALTY, CPF, BIRTH_DATE, PHONE);
    private final DoctorResponse response = new DoctorResponse(ID, EMAIL, SPECIALTY.getDescription(), CPF, BIRTH_DATE, PHONE);

    @BeforeEach
    void setup() {
        this.service = new DoctortService(repository, mapper);
    }

    @Test
    @DisplayName("Return the list of all doctors when calling getAllDoctors")
    void shouldReturnListDoctors_WhenCallingGetAllDoctors() {
        // Given
        given(repository.findAll()).willReturn(singletonList(doctor));

        // Given
        var actual = this.service.getAllDoctors();

        // Then
        assertThat(actual.isEmpty(), is(false));
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0), is(response));

        verify(this.repository).findAll();
        verifyNoMoreInteractions(this.repository);
    }

    @Test
    @DisplayName("Should get a doctor by ID")
    void shouldGetDoctor_WhenCallingGetDoctorById_WhenIdExist() {
        // Given
        given(repository.findById(anyLong())).willReturn(Optional.of(doctor));

        var actual = this.service.getDoctorById(1L);

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
        var expectedMessage = "Doctor not found!";

        // Then
        assertThatThrownBy(() -> service.getDoctorById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    @DisplayName("Given valid doctor request, it should save the doctor")
    void shouldSaveDoctor_SuccessfullyWhenCallingSaveDoctor() {
        // Given
        given(repository.save(any(Doctor.class))).willReturn(doctor);

        // When
        var request = new DoctorRequest(EMAIL, PASSWORD, COMFIRM_PASSWORD, SPECIALTY, CPF, BIRTH_DATE, PHONE);
        var actual = this.service.saveDoctor(request);

        // Then
        assertNotNull(actual);
        assertThat(actual, is(response));

        verify(repository).save(any(Doctor.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should throw PasswordDoesNotMatch when passwords does not match")
    void shouldThrowPasswordDoesNotMatch_WhenPasswordsDoesNotMatch() {
        // Given
        var request = new DoctorRequest(EMAIL, PASSWORD, "123", SPECIALTY, CPF, BIRTH_DATE, PHONE);
        var expectedMessage = "Password does not match!";

        // Then
        assertThatThrownBy(() -> service.saveDoctor(request))
                .isInstanceOf(PasswordDoesNotMatch.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    @DisplayName("Should Update doctor successfully when Id exist")
    void shouldUpdateDoctor_SuccessfullyWhenCallingUpdateDoctor_WhenIdExist() {
        var updateEmail = "cleonildo.junior@outlook.com";
        var updateSpecialty = Specialty.PSYCHIATRIST;
        var updateCPF = "093.947.630-40";
        var updateBirthdate = LocalDate.of(1991,12, 28);
        var updatePhone = "(21) 99982-6188";
        
        var updateDoctor = new Doctor(ID, updateEmail, PASSWORD, updateSpecialty, updateCPF, updateBirthdate, updatePhone);

        // Given
        given(repository.findById(anyLong())).willReturn(Optional.of(doctor));
        given(repository.save(any(Doctor.class))).willReturn(updateDoctor);

        // When
        var request = new DoctorRequestNoPassword(updateEmail, updateSpecialty, updateCPF, updateBirthdate, updatePhone);
        var doctorResponseUpdate = new DoctorResponse(ID, updateEmail, updateSpecialty.getDescription(), updateCPF,
                updateBirthdate, updatePhone);
        var actual = this.service.updateDoctor(ID, request);

        // Then
        assertNotNull(actual);
        assertThat(actual, is(doctorResponseUpdate));

        verify(repository).save(any(Doctor.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should throw NotFoundException when ID does not exist when calling updateDoctor")
    void shouldThrowNotFoundException_WhenCallingUpdateDoctor_WhenIdDoesNotExist() {
        var updateEmail = "cleonildo.junior@outlook.com";
        var updateSpecialty = Specialty.PSYCHIATRIST;
        var updateCPF = "093.947.630-40";
        var updateBirthdate = LocalDate.of(1991,12, 28);
        var updatePhone = "(21) 99982-6188";

        // Given
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        var expectedMessage = "Doctor not found!";

        var request = new DoctorRequestNoPassword(updateEmail, updateSpecialty, updateCPF, updateBirthdate, updatePhone);

        // Then
        assertThatThrownBy(() -> service.updateDoctor(1L, request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    @DisplayName("Should delete a doctor successsfully when calling deleteDoctortById when Id exist")
    void shouldDeleteDoctorSuccessfully_WhenCalliDeleteDoctortById_WhenIdExist() {
        // Given
        given(repository.findById(anyLong())).willReturn(Optional.of(doctor));
        willDoNothing().given(repository).delete(any(Doctor.class));

        this.service.deleteDoctortById(ID);

        // Then
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).delete(doctor);
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("Should throw NotFoundException when ID does not exist when calling deleteDoctortById")
    void shouldThrowNotFoundException_WhenIdDoesNotExist_WhenCallingDeleteDoctortById() {
        // Given
        given(repository.findById(anyLong())).willReturn(Optional.empty());
        var expectedMessage = "Doctor not found!";

        // Then
        assertThatThrownBy(() -> service.deleteDoctortById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(expectedMessage);
    }

}