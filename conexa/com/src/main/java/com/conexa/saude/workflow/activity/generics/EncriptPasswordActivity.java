package com.conexa.saude.workflow.activity.generics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EncriptPasswordActivity  {

    @Autowired
    private PasswordEncoder passwordEncoder;

     public String doExecute(String password) {

        return passwordEncoder.encode(password);
       
     }
        
}
   

