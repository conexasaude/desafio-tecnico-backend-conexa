package com.conexa.saude.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.conexa.saude.repositories.InvalidTokenRepository;
import com.conexa.saude.workflow.activity.generics.BaseActivity;
import com.conexa.saude.workflow.activity.logOffActivities.SaveInvalidTokenActivity;

@Service
public class LogOffWorkFlow implements BaseActivity<String, Void> {

    @Autowired
    private SaveInvalidTokenActivity saveTokenActivity;

    @Autowired
    private InvalidTokenRepository repository;

    @Override
    public Void doExecute(String token) {
        repository.deleteOldTokens();
        saveTokenActivity.doExecute(token);

        SecurityContextHolder.getContext().setAuthentication(null);

        return null;

    }

}
