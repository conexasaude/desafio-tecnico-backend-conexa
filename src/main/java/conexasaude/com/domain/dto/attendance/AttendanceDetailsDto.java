package conexasaude.com.domain.dto.attendance;

import conexasaude.com.domain.dto.doctor.DoctorUserCreateDto;
import conexasaude.com.domain.dto.patient.PatientCreateDto;
import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceDetailsDto {


    private Timestamp dateHour;

    private PatientCreateDto patient;

    private DoctorUserCreateDto doctor;

}
