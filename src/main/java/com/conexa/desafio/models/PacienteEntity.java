package com.conexa.desafio.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "paciente")
public class PacienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf")
    private String cpf;

    @OneToMany(mappedBy = "paciente")
    private Set<ConsultaEntity> consultas;
}
