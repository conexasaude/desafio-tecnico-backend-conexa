package br.com.cleonildo.schedulingappoinment.serivce;


import br.com.cleonildo.schedulingappoinment.dto.AttendanceRequest;
import br.com.cleonildo.schedulingappoinment.dto.AttendanceResponse;
import br.com.cleonildo.schedulingappoinment.entities.Attendance;
import br.com.cleonildo.schedulingappoinment.exceptions.NotFoundException;
import br.com.cleonildo.schedulingappoinment.mapper.AttendanceMapper;
import br.com.cleonildo.schedulingappoinment.repository.AttendanceRepository;
import br.com.cleonildo.schedulingappoinment.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static br.com.cleonildo.schedulingappoinment.exceptions.ExceptionsConstants.ATTENDANCE_ID_NOT_FOUND;
import static br.com.cleonildo.schedulingappoinment.exceptions.ExceptionsConstants.PATIENT_ID_NOT_FOUND;
import static br.com.cleonildo.schedulingappoinment.logs.constants.AttendanceLogConstants.ATTENDANCE_ID_FOUND;
import static br.com.cleonildo.schedulingappoinment.logs.constants.AttendanceLogConstants.ATTENDANCE_LIST;
import static br.com.cleonildo.schedulingappoinment.logs.constants.AttendanceLogConstants.ATTENDANCE_SAVED_SUCCESSFULLY;
import static br.com.cleonildo.schedulingappoinment.logs.constants.AttendanceLogConstants.ATTENDANCE_WITH_ID_NOT_FOUND;
import static br.com.cleonildo.schedulingappoinment.logs.constants.PatientLogConstants.PATIENT_WITH_ID_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class AttendanceService {
    private static final Logger LOG = LoggerFactory.getLogger(AttendanceService.class);
    private final AttendanceRepository repository;
    private final PatientRepository patientRepository;
    private final AttendanceMapper mapper;

    @Transactional(readOnly = true)
    public List<AttendanceResponse> getAllAttendances() {
        var responseList = this.repository
                .findAll()
                .stream()
                .filter(Objects::nonNull)
                .map(mapper)
                .toList();

        LOG.info(ATTENDANCE_LIST);
        return responseList;
    }

    @Transactional(readOnly = true)
    public AttendanceResponse getAttendanceById(Long id) {
        var attendance = this.repository.findById(id).orElseThrow(() ->  {
            LOG.warn(ATTENDANCE_WITH_ID_NOT_FOUND, id);
            return new NotFoundException(ATTENDANCE_ID_NOT_FOUND);
        });


        LOG.info(ATTENDANCE_ID_FOUND, id);
        return this.mapper.apply(attendance);
    }

    public AttendanceResponse saveAttendance(AttendanceRequest request) {
        var patient = this.patientRepository.findById(request.patientId()).orElseThrow(() -> {
            LOG.warn(PATIENT_WITH_ID_NOT_FOUND, request.patientId());
            return new NotFoundException(PATIENT_ID_NOT_FOUND);
        });

        var attendance = new Attendance(request.dateHour(), patient);

        LOG.info(ATTENDANCE_SAVED_SUCCESSFULLY);
        return this.mapper.apply(repository.save(attendance));
    }
}
