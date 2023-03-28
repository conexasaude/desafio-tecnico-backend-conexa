package  com.conexa.saude.model.entity;

import java.util.Date;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Builder
@Table(name = "Doctor")
@AttributeOverride(name = "id", column = @Column(name = "id_medico"))
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class DoctorEntity extends BaseEntityEntity {

	@Column(name = "nome", length = 50)
	private String nome;
	
	@Column(name = "email", length = 50, unique = true, nullable = false)
	private String email;
	
	@Column(name = "senha",  length = 240, nullable = false)
	private String senha;
		
	@Column(name = "especialidade",  length = 30, nullable = false)
	private String especialidade;
	
	@Column(name = "cpf",  length = 11, unique = true, nullable = false)
	private String cpf;
	
	@Column(name = "dt_nascimento", nullable = false)
	private Date dataNascimento;
	
	@Column(name = "telefone",  length = 11, nullable = false) 
	private String telefone;
	
    
}
