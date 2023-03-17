package com.conexa.desafio.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "token")
    private String token;

    @OneToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "usuario_token", unique = true)
    private UsuarioEntity usuario;
}
