package com.conexa.saude.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.saude.model.dto.DoctorDTO;
import com.conexa.saude.model.dto.DoctorMinimalDTO;
import com.conexa.saude.workflow.activity.generics.BaseActivity;
import com.conexa.saude.workflow.activity.generics.ValidateCpfActivity;
import com.conexa.saude.workflow.activity.saveDoctorActivities.NormalizeCpfActivity;
import com.conexa.saude.workflow.activity.saveDoctorActivities.NormalizePhoneActivity;
import com.conexa.saude.workflow.activity.saveDoctorActivities.PasswordConfirmationActivity;
import com.conexa.saude.workflow.activity.saveDoctorActivities.SaveDoctorActivity;
import com.conexa.saude.workflow.activity.saveDoctorActivities.ValidateCpfAndEmailExistsActivity;
import com.conexa.saude.workflow.activity.saveDoctorActivities.ValidateEmailActivity;

@Service
public class SaveDoctorWorkFlow implements BaseActivity<DoctorDTO, DoctorMinimalDTO> {

    @Autowired
    private NormalizeCpfActivity normalizeCpf;

    @Autowired
    private NormalizePhoneActivity normalizePhoneActivity;

    @Autowired
    private ValidateCpfActivity validateCpfActivity;

    @Autowired
    private ValidateEmailActivity validaEmailActivity;

    @Autowired
    private SaveDoctorActivity saveDoctorActivity;

    @Autowired
    private PasswordConfirmationActivity passwordConfirmationActivity;

    @Autowired
    private ValidateCpfAndEmailExistsActivity validateCpfAndEmailExistsActivity;

    @Override
    public DoctorMinimalDTO doExecute(DoctorDTO doctorDTO) {
        var cpfNormalize = normalizeCpf.doExecute(doctorDTO.getCpf());
        doctorDTO.setCpf(cpfNormalize);
        normalizePhoneActivity.doExecute(doctorDTO);
        validateCpfActivity.doExecute(doctorDTO.getCpf());
        validaEmailActivity.doExecute(doctorDTO.getEmail());
        passwordConfirmationActivity.doExecute(doctorDTO);
        validateCpfAndEmailExistsActivity.doExecute(doctorDTO);
        return saveDoctorActivity.doExecute(doctorDTO);
    }
}
