package com.conexa.desafio.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "consulta")
public class ConsultaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "dataHora")
    private Date dataHora;

    @ManyToOne
    @JoinColumn(name = "consulta_paciente", nullable = false)
    private PacienteEntity paciente;
}
