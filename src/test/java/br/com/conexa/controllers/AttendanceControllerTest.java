package br.com.conexa.controllers;

import br.com.conexa.domain.attendance.Attendance;
import br.com.conexa.domain.attendance.AttendanceRequestDTO;
import br.com.conexa.domain.attendance.PacienteRequestDTO;
import br.com.conexa.infra.security.TokenService;
import br.com.conexa.services.AttendanceService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static br.com.conexa.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class AttendanceControllerTest {

    @Mock
    private AttendanceService attendanceService;

    @Mock
    private TokenService tokenService;
    @InjectMocks
    private AttendanceController attendanceController;

    @Test
    public void newAttendance() throws Exception {

        PacienteRequestDTO pacienteRequestDTO = new PacienteRequestDTO(NOME_PACIENTE,CPF_PACIENTE);
        AttendanceRequestDTO attendanceRequestDTO = new AttendanceRequestDTO(DATA_NASCIMENTO_PACIENTE,pacienteRequestDTO);
        Attendance newAttendance = new Attendance(attendanceRequestDTO);

        when(attendanceService.newAttendance(attendanceRequestDTO)).thenReturn(Optional.of(newAttendance));
        String authorizationHeader = TOKEN;
        when(tokenService.extractToken(authorizationHeader)).thenReturn(authorizationHeader);

        //FIXME
        ResponseEntity responseEntity = attendanceController.newAttendance(authorizationHeader,attendanceRequestDTO);

        // Verificar se a resposta é 200 OK
        assertEquals(200, responseEntity.getStatusCodeValue());

    }
}