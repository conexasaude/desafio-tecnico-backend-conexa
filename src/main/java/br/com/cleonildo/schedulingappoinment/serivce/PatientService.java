package br.com.cleonildo.schedulingappoinment.serivce;


import br.com.cleonildo.schedulingappoinment.dto.PatientRequest;
import br.com.cleonildo.schedulingappoinment.dto.PatientResponse;
import br.com.cleonildo.schedulingappoinment.entities.Patient;
import br.com.cleonildo.schedulingappoinment.exceptions.NotFoundException;
import br.com.cleonildo.schedulingappoinment.mapper.PatientMapper;
import br.com.cleonildo.schedulingappoinment.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.cleonildo.schedulingappoinment.exceptions.ExceptionsConstants.PATIENT_ID_NOT_FOUND;
import static br.com.cleonildo.schedulingappoinment.logs.constants.PatientLogConstants.PATIENT_DELETED_SUCCESSFULLY;
import static br.com.cleonildo.schedulingappoinment.logs.constants.PatientLogConstants.PATIENT_ID_FOUND;
import static br.com.cleonildo.schedulingappoinment.logs.constants.PatientLogConstants.PATIENT_LIST;
import static br.com.cleonildo.schedulingappoinment.logs.constants.PatientLogConstants.PATIENT_SAVED_SUCCESSFULLY;
import static br.com.cleonildo.schedulingappoinment.logs.constants.PatientLogConstants.PATIENT_UPDATE_SUCCESSFULLY;
import static br.com.cleonildo.schedulingappoinment.logs.constants.PatientLogConstants.PATIENT_WITH_ID_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientService {
    private static final Logger LOG = LoggerFactory.getLogger(PatientService.class);
    private final PatientRepository repository;
    private final PatientMapper mapper;

    @Transactional(readOnly = true)
    public List<PatientResponse> getAllPatients() {
        var responseList = repository
                .findAll()
                .stream()
                .map(mapper)
                .toList();

        LOG.info(PATIENT_LIST);
        return responseList;
    }

    @Transactional(readOnly = true)
    public PatientResponse getPatientById(Long id) {
        var response = repository.findById(id).orElseThrow(() -> {
            LOG.warn(PATIENT_WITH_ID_NOT_FOUND, id);
            return new NotFoundException(PATIENT_ID_NOT_FOUND);
        });

        LOG.info(PATIENT_ID_FOUND, id);
        return this.mapper.apply(response);
    }

    public PatientResponse savePatient(PatientRequest request) {
        var patient = new Patient(request.name(), request.cpf());
        var response = this.mapper.apply(repository.save(patient));

        LOG.info(PATIENT_SAVED_SUCCESSFULLY);
        return response;
    }

    public PatientResponse updatePatient(Long id, PatientRequest request) {
        var response = repository.findById(id).orElseThrow(() -> {
            LOG.warn(PATIENT_WITH_ID_NOT_FOUND, id);
            return new NotFoundException(PATIENT_ID_NOT_FOUND);
        });

        response.setName(request.name());
        response.setCpf(request.cpf());

        LOG.info(PATIENT_UPDATE_SUCCESSFULLY);
        return this.mapper.apply(repository.save(response));
    }

    public void deletePatientById(Long id) {
        var response = repository.findById(id).orElseThrow(() -> {
            LOG.warn(PATIENT_WITH_ID_NOT_FOUND, id);
            return new NotFoundException(PATIENT_ID_NOT_FOUND);
        });

        this.repository.delete(response);
        LOG.info(PATIENT_DELETED_SUCCESSFULLY);
    }

}
