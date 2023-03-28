package com.conexa.saude.model.entity;

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
@Table(name = "paciente")
@AttributeOverride(name = "id", column = @Column(name = "id_paciente"))
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor
@AllArgsConstructor
public class PacienteEntity extends BaseEntity {

	@Column(name = "nome", length = 50, unique = true, nullable = false)
	private String nome;

	@Column(name = "cpf", length = 11, unique = true, nullable = false)
	private String cpf;

}
