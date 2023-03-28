package com.conexa.saude.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
@AllArgsConstructor
public class PacientDTO {

    @JsonIgnore
    private String id;
    
    private String nome;

    private String cpf;
    
}
