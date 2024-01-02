package conexasaude.com;

import conexasaude.com.domain.dto.attendance.AttendanceCreateDto;
import conexasaude.com.domain.dto.attendance.AttendanceDetailsDto;
import conexasaude.com.domain.entity.Attendance;
import conexasaude.com.domain.service.AttendanceService;
import conexasaude.com.domain.service.ModelMapperService;
import conexasaude.com.rest.AttendanceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AttendanceControllerTest {

    @Mock
    private AttendanceService attendanceService;

    @Mock
    private ModelMapperService modelMapperService;

    @InjectMocks
    private AttendanceController attendanceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateAttendance() {
        AttendanceCreateDto createDto = new AttendanceCreateDto(); // criar um AttendanceCreateDto válido para o teste
        Attendance attendance = new Attendance(); // criar um Attendance simulado para o teste
        AttendanceDetailsDto detailsDto = new AttendanceDetailsDto(); // criar um AttendanceDetailsDto simulado para o teste

        // Simular o comportamento dos serviços
        when(modelMapperService.toObject(Attendance.class, createDto)).thenReturn(attendance);
        when(attendanceService.createAttendance(attendance)).thenReturn(attendance);
        when(modelMapperService.toObject(AttendanceDetailsDto.class, attendance)).thenReturn(detailsDto);

        ResponseEntity<Object> response = attendanceController.createAttendance(createDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(modelMapperService, times(1)).toObject(Attendance.class, createDto);
        verify(attendanceService, times(1)).createAttendance(attendance);
        verify(modelMapperService, times(1)).toObject(AttendanceDetailsDto.class, attendance);
    }
}
