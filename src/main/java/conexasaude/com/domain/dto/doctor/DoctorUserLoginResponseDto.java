package conexasaude.com.domain.dto.doctor;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUserLoginResponseDto {

    private String token;
}
