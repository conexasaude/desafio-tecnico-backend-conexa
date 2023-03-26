package com.conexa.saude.workflow.activity.LogOffActivities;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.conexa.saude.constants.ServiceConstants.*;
import com.conexa.saude.model.entity.InvalidTokenEntity;
import com.conexa.saude.repositories.InvalidTokenRepository;
import com.conexa.saude.workflow.activity.generics.BaseActivity;

@Service
public class SaveInvalidTokenActivity implements BaseActivity<String, Void> {

    @Value("${jwt.expiration-milliseconds}")
    private int expiration;

    @Autowired
    private InvalidTokenRepository repository;

    @Override 
    public Void doExecute(String token) { 
        
        InvalidTokenEntity invalidToken =  InvalidTokenEntity.builder()
        .token(token.substring(BEARER.length()))
        .expirationDate(new Date(new Date().getTime() + expiration))
        .build();
        

        repository.save(invalidToken);

        return null;

    }

}
