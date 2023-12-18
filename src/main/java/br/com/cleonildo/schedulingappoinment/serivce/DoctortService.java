package br.com.cleonildo.schedulingappoinment.serivce;


import br.com.cleonildo.schedulingappoinment.dto.DoctorRequest;
import br.com.cleonildo.schedulingappoinment.dto.DoctorRequestNoPassword;
import br.com.cleonildo.schedulingappoinment.dto.DoctorResponse;
import br.com.cleonildo.schedulingappoinment.entities.Doctor;
import br.com.cleonildo.schedulingappoinment.exceptions.NotFoundException;
import br.com.cleonildo.schedulingappoinment.exceptions.PasswordDoesNotMatch;
import br.com.cleonildo.schedulingappoinment.mapper.DoctorMapper;
import br.com.cleonildo.schedulingappoinment.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.cleonildo.schedulingappoinment.exceptions.ExceptionsConstants.DOCTOR_ID_NOT_FOUND;
import static br.com.cleonildo.schedulingappoinment.logs.constants.DoctorLogConstants.DOCTOR_DELETED_SUCCESSFULLY;
import static br.com.cleonildo.schedulingappoinment.logs.constants.DoctorLogConstants.DOCTOR_ID_FOUND;
import static br.com.cleonildo.schedulingappoinment.logs.constants.DoctorLogConstants.DOCTOR_LIST;
import static br.com.cleonildo.schedulingappoinment.logs.constants.DoctorLogConstants.DOCTOR_PASSWORDS_DOES_NOT_MATCH;
import static br.com.cleonildo.schedulingappoinment.logs.constants.DoctorLogConstants.DOCTOR_SAVED_SUCCESSFULLY;
import static br.com.cleonildo.schedulingappoinment.logs.constants.DoctorLogConstants.DOCTOR_UPDATE_SUCCESSFULLY;
import static br.com.cleonildo.schedulingappoinment.logs.constants.DoctorLogConstants.DOCTOR_WITH_ID_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class DoctortService {
    private static final Logger LOG = LoggerFactory.getLogger(DoctortService.class);
    private final DoctorRepository repository;
    private final DoctorMapper mapper;

    @Transactional(readOnly = true)
    public List<DoctorResponse> getAllDoctors() {
        var responseList = this.repository
                .findAll()
                .stream()
                .map(mapper)
                .toList();

        LOG.info(DOCTOR_LIST);
        return responseList;
    }

    @Transactional(readOnly = true)
    public DoctorResponse getDoctorById(Long id) {
        var doctorOptional = this.repository.findById(id);

        if (doctorOptional.isEmpty()) {
            LOG.warn(DOCTOR_WITH_ID_NOT_FOUND, id);
            throw new NotFoundException(DOCTOR_ID_NOT_FOUND);
        }

        LOG.info(DOCTOR_ID_FOUND, id);
        return this.mapper.apply(doctorOptional.get());
    }

    public DoctorResponse saveDoctor(DoctorRequest request) {
        var isPasswordsEquals = request.password().equals(request.confirmPassword());

        if (!isPasswordsEquals) {
            LOG.warn(DOCTOR_PASSWORDS_DOES_NOT_MATCH);
            throw new PasswordDoesNotMatch();
        }

        var doctor = this.buildDoctorFromRequest(request);
        var response = this.mapper.apply(repository.save(doctor));

        LOG.info(DOCTOR_SAVED_SUCCESSFULLY);
        return response;
    }

    private Doctor buildDoctorFromRequest(DoctorRequest request) {
        return new Doctor(
                request.email(),
                request.password(),
                request.specialty(),
                request.cpf(),
                request.birthdate(),
                request.phone()
        );
    }

    public DoctorResponse updateDoctor(Long id, DoctorRequestNoPassword request) {
        var doctorOptional = this.repository.findById(id);

        if (doctorOptional.isEmpty()) {
            LOG.warn(DOCTOR_WITH_ID_NOT_FOUND, id);
            throw new NotFoundException(DOCTOR_ID_NOT_FOUND);
        }

        this.updateDoctorFromRequest(request, doctorOptional.get());
        var response = this.mapper.apply(repository.save(doctorOptional.get()));

        LOG.info(DOCTOR_UPDATE_SUCCESSFULLY);
        return response;
    }

    private void updateDoctorFromRequest(DoctorRequestNoPassword request, Doctor doctor) {
        doctor.setEmail(request.email());
        doctor.setSpecialty(request.specialty());
        doctor.setCpf(request.cpf());
        doctor.setBirthdate(request.birthdate());
        doctor.setPhone(request.phone());
    }

    public void deleteDoctortById(Long id) {
        var doctorOptional = this.repository.findById(id);

        if (doctorOptional.isEmpty()) {
            LOG.warn(DOCTOR_WITH_ID_NOT_FOUND, id);
            throw new NotFoundException(DOCTOR_ID_NOT_FOUND);
        }

        this.repository.delete(doctorOptional.get());
        LOG.info(DOCTOR_DELETED_SUCCESSFULLY);
    }

}
