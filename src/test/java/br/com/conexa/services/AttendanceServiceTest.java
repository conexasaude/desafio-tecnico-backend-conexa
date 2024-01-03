package br.com.conexa.services;

import br.com.conexa.domain.attendance.Attendance;
import br.com.conexa.domain.attendance.AttendanceRequestDTO;
import br.com.conexa.domain.attendance.PacienteRequestDTO;
import br.com.conexa.repositories.AttendanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static br.com.conexa.constants.Constants.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class AttendanceServiceTest {


    @Autowired
    @InjectMocks
    private AttendanceService attendanceService;
    @Mock
    private AttendanceRepository attendanceRepository;

    //private Validator validator;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        /*ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

         */
    }

    @Test
    @DisplayName("Should create a new attendance successfully when everything is OK")
    void newAttendance() {

        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO(NOME_PACIENTE,CPF_PACIENTE);
        AttendanceRequestDTO attendanceRequestDTO = new AttendanceRequestDTO(DATA_NASCIMENTO_PACIENTE,pacienteRequestDTO);
        Attendance newAttendance = new Attendance(attendanceRequestDTO);

        when(this.attendanceRepository.save(newAttendance)).thenReturn(newAttendance);

        this.attendanceService.newAttendance(attendanceRequestDTO);
        verify(attendanceRepository, times(1)).save(newAttendance);
    }
}