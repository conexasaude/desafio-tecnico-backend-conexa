package conexasaude.com.domain.dto.attendance;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import conexasaude.com.domain.dto.patient.PatientCreateDto;
import lombok.*;

import java.sql.Timestamp;
import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceCreateDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonProperty("dataHora")
    private Timestamp dateHour;

    @JsonProperty("paciente")
    private PatientCreateDto patient;


}
