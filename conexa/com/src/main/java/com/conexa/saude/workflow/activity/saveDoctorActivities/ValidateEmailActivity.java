package com.conexa.saude.workflow.activity.saveDoctorActivities;

import javax.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

import com.conexa.saude.excepetions.BadRequestException;
import com.conexa.saude.workflow.activity.generics.BaseActivity;

@Service
public class ValidateEmailActivity implements BaseActivity<String, Void> {

    @Override
    public Void doExecute(String email) {
        try{ 
            InternetAddress internetAddress = new InternetAddress(email); 
            internetAddress.validate(); 
        }catch(Exception e){ 
            throw new BadRequestException(String.format("Email invalido: %s", email));
        } 
        
        return null;
    }
    
}
