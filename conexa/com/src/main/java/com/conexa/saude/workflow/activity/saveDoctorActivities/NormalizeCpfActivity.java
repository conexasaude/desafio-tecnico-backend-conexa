package com.conexa.saude.workflow.activity.saveDoctorActivities;

import org.springframework.stereotype.Service;

import com.conexa.saude.workflow.activity.generics.BaseActivity;

@Service
public class NormalizeCpfActivity implements BaseActivity<String, String> {

    @Override
    public String doExecute(String cpf) {
        return cpf.replaceAll("\\D", "");

    }

}
