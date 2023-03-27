package com.conexa.saude.workflow.activity.saveDoctorActivities;

import org.springframework.stereotype.Service;

import com.conexa.saude.excepetions.BadRequestException;
import com.conexa.saude.model.dto.DoctorDTO;
import com.conexa.saude.workflow.activity.generics.BaseActivity;

@Service
public class PasswordConfirmationActivity implements BaseActivity<DoctorDTO, Void>{

    @Override
    public Void doExecute(DoctorDTO doctorDTO) {
        var validatePassword = doctorDTO.getSenha().equals(doctorDTO.getConfirmacaoSenha());
       
        if(!validatePassword){
            throw new BadRequestException("As senhas não são iguais. Tente novamente.");
        }

        return null;

    }
    
}
