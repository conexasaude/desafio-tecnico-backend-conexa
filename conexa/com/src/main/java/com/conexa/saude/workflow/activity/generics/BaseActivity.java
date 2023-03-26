package com.conexa.saude.workflow.activity.generics;

public interface BaseActivity <I, O> {
    
    public O doExecute(I input);
    
} 
