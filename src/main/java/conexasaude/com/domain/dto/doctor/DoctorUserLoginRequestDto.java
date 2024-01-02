package conexasaude.com.domain.dto.doctor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUserLoginRequestDto {

    @JsonProperty("senha")
    private String password;

    private String email;
}
