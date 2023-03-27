package com.conexa.saude.workflow.activity.RegisterAttendenceActivities;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.conexa.saude.excepetions.BadRequestException;
import com.conexa.saude.model.dto.AttendanceDTO;
import com.conexa.saude.workflow.activity.generics.BaseActivity;

@Service
public class ValidateDateTimeActivity implements BaseActivity<AttendanceDTO, Void> {

    @Override
    public Void doExecute(AttendanceDTO attendanceDTO) { 
        Date actual = new Date();

        var infoPacient = attendanceDTO.getDataHora();

        if(infoPacient.before(actual)){
            throw new BadRequestException(String.format("Horario informado invalido, favor verificar se a data esta no futuro: %s", infoPacient));
        }

        return null;


    }


}
