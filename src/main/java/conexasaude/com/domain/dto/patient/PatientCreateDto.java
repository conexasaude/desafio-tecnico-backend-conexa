package conexasaude.com.domain.dto.patient;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientCreateDto {


    @JsonProperty("nome")
    private String name;

    private String cpf;
}
