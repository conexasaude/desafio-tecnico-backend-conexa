package br.com.conexa.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import br.com.conexa.vo.PacienteVO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;

@Entity
@Table(name = "consulta")
public class Consulta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cdConsulta;
	
	@Column(name = "dt_hora")
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date dataHora;
	
	@Column(name = "cd_paciente")
	private Integer cdPaciente;
	
	@Transient
	@JsonProperty("paciente")
	private PacienteVO pacienteVO;

	public Integer getCdConsulta() {
		return cdConsulta;
	}

	public void setCdConsulta(Integer cdConsulta) {
		this.cdConsulta = cdConsulta;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public Integer getCdPaciente() {
		return cdPaciente;
	}

	public void setCdPaciente(Integer cdPaciente) {
		this.cdPaciente = cdPaciente;
	}

	public PacienteVO getPacienteVO() {
		return pacienteVO;
	}

	public void setPacienteVO(PacienteVO pacienteVO) {
		this.pacienteVO = pacienteVO;
	}

	 
	
	

	

}
