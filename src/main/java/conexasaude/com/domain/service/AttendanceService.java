package conexasaude.com.domain.service;

import conexasaude.com.domain.entity.Attendance;
import conexasaude.com.domain.entity.Patient;
import conexasaude.com.domain.exceptions.InvalidInfoException;
import conexasaude.com.domain.exceptions.MissingInfoException;
import conexasaude.com.domain.repository.AttendanceRepository;
import conexasaude.com.domain.repository.PatientRepository;
import conexasaude.com.domain.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final PatientRepository patientRepository;
    private final AttendanceRepository attendanceRepository;

    @Transactional
    public Attendance createAttendance(Attendance attendance) {

        validatePatient(attendance.getPatient());
        validateFutureTimestamp(attendance.getDateHour());

        return attendanceRepository.save(Attendance.builder()
                .patient(getByCpfOrCreateNewPatient(attendance.getPatient()))
                .doctorUser(Utils.getLoggedInUser())
                .dateHour(attendance.getDateHour())
                .build());
    }

    @NotNull
    private Patient getByCpfOrCreateNewPatient(Patient patient) {
        var patientCpf = Utils.removeNonNumericCharacters(patient.getCpf());
        var patientName= patient.getName();
        return patientRepository.findByCpf(patientCpf)
            .orElse(patientRepository.save(Patient.builder().cpf(patientCpf).name(patientName).build()));
    }

    private static void validatePatient(Patient patient) {
        if (Optional.ofNullable(patient).isEmpty())
            throw new MissingInfoException("Informações do paciente faltando");

        if (Optional.ofNullable(patient.getCpf()).isEmpty() || patient.getCpf().isEmpty())
            throw new MissingInfoException("CPF do paciente faltando");

        if (Optional.ofNullable(patient.getName()).isEmpty() || patient.getName().isEmpty())
            throw new MissingInfoException("Nome do paciente faltando");
    }

    private static void validateFutureTimestamp(Timestamp timestamp) {
        if (Optional.ofNullable(timestamp).isEmpty())
            throw new MissingInfoException("Data e hora faltando");

        if (timestamp.toInstant().isBefore(Instant.now()))
            throw new InvalidInfoException("Atendimento deve ser agendado para uma data futura");
    }
}
