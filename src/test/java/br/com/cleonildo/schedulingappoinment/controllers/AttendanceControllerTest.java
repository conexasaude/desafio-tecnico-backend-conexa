package br.com.cleonildo.schedulingappoinment.controllers;

import br.com.cleonildo.schedulingappoinment.dto.AttendanceRequest;
import br.com.cleonildo.schedulingappoinment.dto.AttendanceResponse;
import br.com.cleonildo.schedulingappoinment.dto.DoctorRequest;
import br.com.cleonildo.schedulingappoinment.dto.DoctorRequestNoPassword;
import br.com.cleonildo.schedulingappoinment.dto.DoctorResponse;
import br.com.cleonildo.schedulingappoinment.dto.PatientResume;
import br.com.cleonildo.schedulingappoinment.enums.Specialty;
import br.com.cleonildo.schedulingappoinment.serivce.AttendanceService;
import br.com.cleonildo.schedulingappoinment.serivce.DoctortService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AttendanceControllerTest {

    @Mock
    private AttendanceService service;
    @InjectMocks
    private AttendanceController controller;
    private MockMvc mockMvc;
    private final static String BASE_URL = "/api/v1/attendance";
    private final static String URL_ID = BASE_URL + "/{id}";
    private static final Long ID = 1L;
    private static final Instant DATE_HOUR = Instant.now().plus(2, ChronoUnit.HOURS);
    private final AttendanceResponse response = new AttendanceResponse(DATE_HOUR, new PatientResume("Fernanda Margolo", "944.480.250-71"));
    private final AttendanceRequest request = new AttendanceRequest(DATE_HOUR, ID);
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setupAttributes() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).alwaysDo(print()).build();
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Should get list attendance and return status code OK")
    void shouldGetListAttendance_AndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.getAllAttendances()).willReturn(singletonList(response));

        // Then
        this.mockMvc
                .perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].dataHora").value(this.formattedDate(response.dateHour())))
                .andExpect(jsonPath("$[0].paciente.nome").value(response.patient().name()))
                .andExpect(jsonPath("$[0].paciente.cpf").value(response.patient().cpf()));


        // Verify
        verify(service).getAllAttendances();
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should get attendance by ID and return status code OK")
    void shouldGetAttendanceById_AndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.getAttendanceById(ID)).willReturn(response);

        // Then
        this.mockMvc
                .perform(get(URL_ID, ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dataHora").value(this.formattedDate(response.dateHour())))
                .andExpect(jsonPath("$.paciente.nome").value(response.patient().name()))
                .andExpect(jsonPath("$.paciente.cpf").value(response.patient().cpf()));

        // Verify
        verify(this.service).getAttendanceById(ID);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    @DisplayName("Should save attendance and return status code CREATED")
    void shouldSaveAttendance_AndReturnStatusCodeCreated() throws Exception {
        // Given
        given(service.saveAttendance(any(AttendanceRequest.class))).willReturn(response);

        // Then
        this.mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // Verify
        verify(this.service).saveAttendance(any(AttendanceRequest.class));
        verifyNoMoreInteractions(this.service);
    }

    private String formattedDate(Instant date) {
        var simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = null;

        try {
            dateString =
                    simpleDateFormat.format(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date.toString()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return dateString;
    }

}