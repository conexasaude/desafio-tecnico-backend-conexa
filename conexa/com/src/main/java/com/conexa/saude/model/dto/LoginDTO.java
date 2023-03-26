package com.conexa.saude.model.dto;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@EqualsAndHashCode
public class LoginDTO implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    private String email;
    
    private String senha;

}
