package com.conexa.saude.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode
@RequiredArgsConstructor
public class PacientDTO {

    @JsonIgnore
    private String id;
    
    private String nome;

    private String cpf;
    
}
