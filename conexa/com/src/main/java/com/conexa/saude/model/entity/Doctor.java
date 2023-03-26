package  com.conexa.saude.model.entity;

import java.util.Date;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;


@Entity
@Data
@Table(name = "Doctor")
@AttributeOverride(name = "id", column = @Column(name = "id_medico"))
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class Doctor extends BaseEntity {

	@Column(name = "nome", length = 50, unique = true, nullable = false)
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
