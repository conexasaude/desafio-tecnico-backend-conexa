package com.conexa.saude.workflow.activity.saveDoctorActivities;

import com.conexa.saude.model.dto.DoctorDTO;
import com.conexa.saude.workflow.activity.generics.BaseActivity;
import org.springframework.stereotype.Service;

@Service
public class NormalizePhoneActivity implements BaseActivity<DoctorDTO, Void> {

    @Override
    public Void doExecute(DoctorDTO input) {
        String normalizedPhone = input.getCpf().replaceAll("\\D", "");
        input.setTelefone(normalizedPhone);

        return null;
    }
    
}
