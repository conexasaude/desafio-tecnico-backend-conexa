package com.conexa.saude.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class DoctorMinimalDTO {

    private String id;
    
    private String nome;

    private String email;
    
	private String especialidade;
    
}
