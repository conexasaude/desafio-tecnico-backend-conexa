package com.conexa.desafio.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
                    property = "id")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "senha")
    private String senha;

    @Column(name = "especialidade")
    private String especialidade;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "dataNascimento")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    @Column(name = "telefone")
    private String telefone;

    @OneToOne(optional = false, mappedBy = "usuario")
    private TokenEntity token;

    @OneToMany(mappedBy = "medico")
    private Set<ConsultaEntity> consultas;

}
