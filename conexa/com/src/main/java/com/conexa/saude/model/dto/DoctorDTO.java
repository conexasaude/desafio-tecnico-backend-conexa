package  com.conexa.saude.model.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    @NotBlank
    private String nome;
    
    @NotBlank
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
