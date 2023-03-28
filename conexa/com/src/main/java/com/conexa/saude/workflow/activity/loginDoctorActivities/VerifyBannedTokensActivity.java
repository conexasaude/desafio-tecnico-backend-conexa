package com.conexa.saude.workflow.activity.loginDoctorActivities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.conexa.saude.repositories.InvalidTokenRepository;
import com.conexa.saude.workflow.activity.generics.BaseActivity;

@Service
public class VerifyBannedTokensActivity implements BaseActivity<String, Boolean> {

    @Autowired
    private InvalidTokenRepository repository;

    @Override
    public Boolean doExecute(String token) {

        var findToken = repository.findByToken(token);

        return findToken.isPresent();

    }

}
