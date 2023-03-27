package  com.conexa.saude.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
public class DoctorDTO {

    @NotBlank
    private String nome;
    
    @NotBlank
    @Email
	private String email;

	@NotBlank
	private String senha;

    @NotBlank
    private String confirmacaoSenha;
	
    @NotBlank
	private String especialidade;
	
    @NotBlank
	private String cpf;
	
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
	private Date dataNascimento;
	
    @NotBlank
	private String telefone;
	
    
}
