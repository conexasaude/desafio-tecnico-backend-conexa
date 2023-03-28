package com.conexa.saude.model.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@Table(name = "invalid_tokens")
@AllArgsConstructor
@NoArgsConstructor
public class InvalidTokenEntity {

	@Id
	@Column(name = "token", length = 360, unique = true, nullable = false)
	private String token;

	@Column(name = "expiration_date", nullable = false)
	private Date expirationDate;

}
