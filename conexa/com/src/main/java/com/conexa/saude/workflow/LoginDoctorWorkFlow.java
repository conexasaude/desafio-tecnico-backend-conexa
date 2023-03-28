package com.conexa.saude.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.saude.model.dto.LoginDTO;
import com.conexa.saude.workflow.activity.generics.BaseActivity;
import com.conexa.saude.workflow.activity.loginDoctorActivities.LoginDoctorActivity;

@Service
public class LoginDoctorWorkFlow implements BaseActivity<LoginDTO, String> {

    @Autowired
    private LoginDoctorActivity loginDoctorActivity;

    @Override
    public String doExecute(LoginDTO loginDTO) {
        return loginDoctorActivity.doExecute(loginDTO);
    }
}
