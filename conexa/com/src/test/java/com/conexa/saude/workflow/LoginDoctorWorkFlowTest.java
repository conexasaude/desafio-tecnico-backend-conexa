package com.conexa.saude.workflow;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.conexa.saude.model.dto.LoginDTO;
import com.conexa.saude.workflow.activity.loginDoctorActivities.LoginDoctorActivity;

@ExtendWith(MockitoExtension.class)
public class LoginDoctorWorkFlowTest {

    @Mock
    private LoginDoctorActivity activity;

    @InjectMocks
    private LoginDoctorWorkFlow target;

    @Test
    public void test() {
        LoginDTO loginDTO = new LoginDTO();

        target.doExecute(loginDTO);

        verify(activity, times(1)).doExecute(Mockito.any(LoginDTO.class));

    }
}
