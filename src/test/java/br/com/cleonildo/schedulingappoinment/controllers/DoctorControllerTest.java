package br.com.cleonildo.schedulingappoinment.controllers;

import br.com.cleonildo.schedulingappoinment.dto.DoctorRequest;
import br.com.cleonildo.schedulingappoinment.dto.DoctorRequestNoPassword;
import br.com.cleonildo.schedulingappoinment.dto.DoctorResponse;
import br.com.cleonildo.schedulingappoinment.dto.PatientRequest;
import br.com.cleonildo.schedulingappoinment.dto.PatientResponse;
import br.com.cleonildo.schedulingappoinment.enums.Specialty;
import br.com.cleonildo.schedulingappoinment.serivce.DoctortService;
import br.com.cleonildo.schedulingappoinment.serivce.PatientService;
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

import java.time.LocalDate;

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
class DoctorControllerTest {

    @Mock
    private DoctortService service;
    @InjectMocks
    private DoctorController controller;
    private MockMvc mockMvc;
    private final static String BASE_URL = "/api/v1/doctors";
    private final static String URL_ID = BASE_URL + "/{id}";
    private static final Long ID = 1L;
    private static final String EMAIL = "doutorwulices@hotmail.com";
    private static final String PASSWORD = "12345";
    private static final String COMFIRM_PASSWORD = "12345";
    private static final Specialty SPECIALTY = Specialty.NEUROLOGIST;
    private static final String SPECIALTY_STRING = SPECIALTY.getDescription();
    private static final String CPF = "944.006.300-99";
    private static final LocalDate BIRTH_DATE = LocalDate.of(1989,12, 28);
    private static final String PHONE = "(21) 97981-6155";
    private final DoctorResponse response = new DoctorResponse(ID, EMAIL, SPECIALTY_STRING, CPF, BIRTH_DATE, PHONE);
    private final DoctorRequest request = new DoctorRequest(EMAIL, PASSWORD, COMFIRM_PASSWORD, SPECIALTY, CPF, BIRTH_DATE, PHONE);
    private final DoctorRequestNoPassword requestNoPassword = new DoctorRequestNoPassword(EMAIL, SPECIALTY, CPF, BIRTH_DATE, PHONE);
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setupAttributes() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).alwaysDo(print()).build();
        this.mapper.registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Should get list of doctors and return status code OK")
    void shouldGetListOfDoctors_AndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.getAllDoctors()).willReturn(singletonList(response));

        // Then
        this.mockMvc
                .perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(response.id()))
                .andExpect(jsonPath("$[0].email").value(response.email()))
                .andExpect(jsonPath("$[0].especialidade").value(response.specialty()))
                .andExpect(jsonPath("$[0].cpf").value(response.cpf()))
                .andExpect(jsonPath("$[0].dataNascimento[0]").value(response.birthdate().getYear()))
                .andExpect(jsonPath("$[0].dataNascimento[1]").value(response.birthdate().getMonthValue()))
                .andExpect(jsonPath("$[0].dataNascimento[2]").value(response.birthdate().getDayOfMonth()))
                .andExpect(jsonPath("$[0].telefone").value(response.phone()));


        // Verify
        verify(service).getAllDoctors();
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should get doctor by ID and return status code OK")
    void shouldGetDoctorById_AndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.getDoctorById(ID)).willReturn(response);

        // Then
        this.mockMvc
                .perform(get(URL_ID, ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.email").value(response.email()))
                .andExpect(jsonPath("$.especialidade").value(response.specialty()))
                .andExpect(jsonPath("$.cpf").value(response.cpf()))
                .andExpect(jsonPath("$.dataNascimento[0]").value(response.birthdate().getYear()))
                .andExpect(jsonPath("$.dataNascimento[1]").value(response.birthdate().getMonthValue()))
                .andExpect(jsonPath("$.dataNascimento[2]").value(response.birthdate().getDayOfMonth()))
                .andExpect(jsonPath("$.telefone").value(response.phone()));

        // Verify
        verify(this.service).getDoctorById(ID);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    @DisplayName("Should save doctor and return status code CREATED")
    void shouldSaveDoctor_AndReturnStatusCodeCreated() throws Exception {
        // Given
        given(service.saveDoctor(any(DoctorRequest.class))).willReturn(response);

        // Then
        this.mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();


        // Verify
        verify(this.service).saveDoctor(any(DoctorRequest.class));
        verifyNoMoreInteractions(this.service);
    }

    @Test
    @DisplayName("Should update doctor and return status code OK")
    void shouldUpdateDoctor_AndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.updateDoctor(ID, requestNoPassword)).willReturn(response);

        // Then
        this.mockMvc
                .perform(put(URL_ID, ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id()))
                .andExpect(jsonPath("$.email").value(response.email()))
                .andExpect(jsonPath("$.especialidade").value(response.specialty()))
                .andExpect(jsonPath("$.cpf").value(response.cpf()))
                .andExpect(jsonPath("$.dataNascimento[0]").value(response.birthdate().getYear()))
                .andExpect(jsonPath("$.dataNascimento[1]").value(response.birthdate().getMonthValue()))
                .andExpect(jsonPath("$.dataNascimento[2]").value(response.birthdate().getDayOfMonth()))
                .andExpect(jsonPath("$.telefone").value(response.phone()));

        // Verify
        verify(this.service).updateDoctor(ID, requestNoPassword);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    @DisplayName("Should delete patient and return status code NO_CONTENT")
    void shouldDeletePatient_AndReturnStatusCodeNoContent() throws Exception {
        // Given
        willDoNothing().given(service).deleteDoctortById(ID);

        // Then
        this.mockMvc
                .perform(delete(URL_ID, ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify
        verify(this.service).deleteDoctortById(ID);
        verifyNoMoreInteractions(this.service);
    }

}