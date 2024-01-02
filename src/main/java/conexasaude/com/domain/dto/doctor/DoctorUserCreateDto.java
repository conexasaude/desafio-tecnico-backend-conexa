package conexasaude.com.domain.dto.doctor;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorUserCreateDto {


    @JsonProperty("senha")
    private String password;

    private String cpf;

    private String email;

    @JsonProperty("especialidade")
    private String specialty;

    @JsonProperty("confirmacaoSenha")
    private String passwordConfirmation;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @JsonProperty("dataNascimento")
    private Date birthday;

    @JsonProperty("telefone")
    private String phone;



}
