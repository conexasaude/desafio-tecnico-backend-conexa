package conexasaude.com;

import conexasaude.com.domain.dto.doctor.DoctorUserCreateDto;
import conexasaude.com.domain.dto.doctor.DoctorUserLoginRequestDto;
import conexasaude.com.domain.dto.doctor.DoctorUserLoginResponseDto;
import conexasaude.com.domain.entity.DoctorUser;
import conexasaude.com.domain.service.AuthenticationService;
import conexasaude.com.domain.service.ModelMapperService;
import conexasaude.com.rest.AuthenticationController;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthenticationControllerTest {

    @Mock
    private ModelMapperService modelMapperService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSignup() {
        DoctorUserCreateDto createDto = new DoctorUserCreateDto(); // criar um DoctorUserCreateDto válido para o teste

        // Simular o comportamento dos serviços
        doNothing().when(authenticationService).save(any(DoctorUser.class));

        ResponseEntity<Object> response = authenticationController.signup(createDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authenticationService, times(1)).save(any(DoctorUser.class));
    }

    @Test
    void testLogin() {
        DoctorUserLoginRequestDto loginRequestDto = new DoctorUserLoginRequestDto(); // criar um DoctorUserLoginRequestDto válido para o teste
        DoctorUserLoginResponseDto loginResponseDto = new DoctorUserLoginResponseDto(); // criar um DoctorUserLoginResponseDto válido para o teste

        // Simular o comportamento dos serviços
        when(authenticationService.login(anyString(), anyString())).thenReturn(loginResponseDto);

        ResponseEntity<DoctorUserLoginResponseDto> response = authenticationController.login(loginRequestDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loginResponseDto, response.getBody());
        verify(authenticationService, times(1)).login(anyString(), anyString());
    }

    @Test
    void testLogoff() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        // Simular o comportamento dos serviços
        doNothing().when(authenticationService).logoff(request);

        ResponseEntity<Object> response = authenticationController.logoff(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authenticationService, times(1)).logoff(request);
    }
}
