package  com.conexa.saude.model.entity;


import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "paciente")
@AttributeOverride(name = "id", column = @Column(name = "id_paciente"))
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class PacienteEntity extends BaseEntityEntity {

	@Column(name = "nome", length = 50, unique = true, nullable = false)
	private String nome;
	
	@Column(name = "cpf",  length = 11, unique = true, nullable = false)
	private String cpf;
	
    
}
