package br.com.cleonildo.schedulingappoinment.controllers;

import br.com.cleonildo.schedulingappoinment.dto.PatientRequest;
import br.com.cleonildo.schedulingappoinment.dto.PatientResponse;
import br.com.cleonildo.schedulingappoinment.serivce.PatientService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class PatientControllerTest {

    @Mock
    private PatientService service;
    @InjectMocks
    private PatientController controller;
    private MockMvc mockMvc;
    private final static String BASE_URL = "/api/v1/patients";
    private final static String URL_ID = BASE_URL + "/{id}";
    private static final Long ID = 1L;
    private static final String NAME = "Fernanda Margolo";
    private static final String CPF =  "944.480.250-71";
    private final PatientResponse response = new PatientResponse(ID, NAME, CPF);
    private final PatientRequest request = new PatientRequest(NAME, CPF);
    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setupAttributes() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).alwaysDo(print()).build();
    }

    @Test
    @DisplayName("Should get list of patients and return status code OK")
    void shouldGetListOfPatients_AndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.getAllPatients()).willReturn(singletonList(response));

        // Then
        this.mockMvc
                .perform(get(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(ID))
                .andExpect(jsonPath("$[0].nome").value(NAME));


        // Verify
        verify(service).getAllPatients();
        verifyNoMoreInteractions(service);
    }

    @Test
    @DisplayName("Should get patient by ID and return status code OK")
    void shouldGetPatientById_AndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.getPatientById(ID)).willReturn(response);

        // Then
        this.mockMvc
                .perform(get(URL_ID, ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ID))
                .andExpect(jsonPath("$.nome").value(NAME));

        // Verify
        verify(this.service).getPatientById(ID);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    @DisplayName("Should save patient and return status code CREATED")
    void shouldSavePatient_AndReturnStatusCodeCreated() throws Exception {
        // Given
        given(service.savePatient(any(PatientRequest.class))).willReturn(response);

        // Then
        this.mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();


        // Verify
        verify(this.service).savePatient(any(PatientRequest.class));
        verifyNoMoreInteractions(this.service);
    }

    @Test
    @DisplayName("Should update patient and return status code OK")
    void shouldUpdatePatient_AndReturnStatusCodeOK() throws Exception {
        // Given
        given(service.updatePatient(ID, request)).willReturn(response);

        // Then
        this.mockMvc
                .perform(put(URL_ID, ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());

        // Verify
        verify(this.service).updatePatient(ID, request);
        verifyNoMoreInteractions(this.service);
    }

    @Test
    @DisplayName("Should delete patient and return status code NO_CONTENT")
    void shouldDeletePatient_AndReturnStatusCodeNoContent() throws Exception {
        // Given
        willDoNothing().given(service).deletePatientById(ID);

        // Then
        this.mockMvc
                .perform(delete(URL_ID, ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Verify
        verify(this.service).deletePatientById(ID);
        verifyNoMoreInteractions(this.service);
    }

}