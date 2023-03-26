package  com.conexa.saude.model.entity;

import java.util.Date;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@Table(name = "attendance")
@AttributeOverride(name = "id", column = @Column(name = "id_attendance"))
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
public class Attendance extends BaseEntity {

	@Column(name = "data_hora", nullable = false)
	private Date dataHora;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_paciente")
	private Paciente paciente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_medico")
	private Doctor doctor;
    
}
