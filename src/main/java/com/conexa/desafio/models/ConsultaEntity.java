package com.conexa.desafio.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
@Entity
@Builder
@Table(name = "consulta")
public class ConsultaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "dataHora")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataHora;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consulta_paciente", nullable = false)
    private PacienteEntity paciente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "consulta_medico", nullable = false)
    private UsuarioEntity medico;
}
