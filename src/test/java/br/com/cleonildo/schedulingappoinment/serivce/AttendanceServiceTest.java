package br.com.cleonildo.schedulingappoinment.serivce;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import br.com.cleonildo.schedulingappoinment.dto.AttendanceRequest;
import br.com.cleonildo.schedulingappoinment.dto.AttendanceResponse;
import br.com.cleonildo.schedulingappoinment.dto.PatientResume;
import br.com.cleonildo.schedulingappoinment.entities.Attendance;
import br.com.cleonildo.schedulingappoinment.entities.Doctor;
import br.com.cleonildo.schedulingappoinment.entities.Patient;
import br.com.cleonildo.schedulingappoinment.exceptions.NotFoundException;
import br.com.cleonildo.schedulingappoinment.mapper.AttendanceMapper;
import br.com.cleonildo.schedulingappoinment.repository.AttendanceRepository;
import br.com.cleonildo.schedulingappoinment.repository.PatientRepository;

import java.time.Instant;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AttendanceServiceTest {

    @Mock
    private AttendanceRepository attendanceRepository;
    @Mock
    private PatientRepository patientRepository;
    private final AttendanceMapper mapper = new AttendanceMapper();
    private AttendanceService service;
    private static final Long ID = 1L;
    private static final Instant DATE_HOUR = Instant.parse("2023-12-26T18:29:54.644-03:00");
    private static final Patient patient = new Patient(1L, "Fernanda Margolo", "944.480.250-71");
    private final AttendanceResponse response = new AttendanceResponse(DATE_HOUR, new PatientResume("Fernanda Margolo", "944.480.250-71"));
    private Attendance attendance;

    @BeforeEach
    void setup() {
        this.service = new AttendanceService(attendanceRepository, patientRepository, mapper);
        this.attendance = new Attendance(ID, DATE_HOUR, patient);
    }

    @Test
    @DisplayName("Return the list of all attendance when calling getAllDoctors")
    void shouldReturnListAttendance_WhenCallingGetAllAttendances() {
        // Given
        given(attendanceRepository.findAll()).willReturn(singletonList(attendance));

        var actual = this.service.getAllAttendances();

        // Then
        assertThat(actual.isEmpty(), is(false));
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0), is(response));

        verify(this.attendanceRepository).findAll();
        verifyNoMoreInteractions(this.attendanceRepository);
    }

    @Test
    @DisplayName("Should get a attendance by ID")
    void shouldGetAttendance_WhenCallingGetAttendanceById_WhenIdExist() {
        // Given
        given(attendanceRepository.findById(anyLong())).willReturn(Optional.of(attendance));

        var actual = this.service.getAttendanceById(1L);

        // Then
        assertNotNull(actual);
        assertThat(actual, is(response));

        verify(attendanceRepository).findById(anyLong());
        verifyNoMoreInteractions(attendanceRepository);
    }

    @Test
    @DisplayName("Should throw NotFoundException when ID does not exist")
    void shouldThrowNotFoundException_WhenIdDoesNotExist() {
        // Given
        given(attendanceRepository.findById(anyLong())).willReturn(Optional.empty());
        var expectedMessage = "Attendance not found!";

        // Then
        assertThatThrownBy(() -> service.getAttendanceById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    @DisplayName("Given valid attendance request, it should save the attendance")
    void shouldSaveAttendance_SuccessfullyWhenCallingSaveAttendance() {
        // Given
        given(attendanceRepository.save(any(Attendance.class))).willReturn(attendance);
        given(patientRepository.findById(anyLong())).willReturn(Optional.of(patient));

        // When
        var request = new AttendanceRequest(DATE_HOUR, ID);
        var actual = this.service.saveAttendance(request);

        // Then
        assertNotNull(actual);
        assertThat(actual, is(response));

        verify(attendanceRepository).save(any(Attendance.class));
        verifyNoMoreInteractions(attendanceRepository);
    }


    @Test
    @DisplayName("Should throw NotFoundException when ID does not exist")
    void shouldThrowNotFoundException_WhenIdDoesNotExist_WhenCallingSaveAttendance_WithPatientIdNoxExisting() {
        // Given
        given(patientRepository.findById(anyLong())).willReturn(Optional.empty());

        var request = new AttendanceRequest(DATE_HOUR, ID);
        var expectedMessage = "Patient not found!";

        // Then
        assertThatThrownBy(() -> service.saveAttendance(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(expectedMessage);
    }

}
