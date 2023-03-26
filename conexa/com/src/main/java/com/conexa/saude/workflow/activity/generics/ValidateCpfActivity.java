package com.conexa.saude.workflow.activity.generics;

import org.springframework.stereotype.Service;

import com.conexa.saude.excepetions.BadRequestException;

import br.com.caelum.stella.validation.CPFValidator;

@Service
public class ValidateCpfActivity implements BaseActivity<String, Boolean>{

    @Override
    public Boolean doExecute(String cpf) { 
        CPFValidator cpfValidator = new CPFValidator(); 
        try{ 
            cpfValidator.assertValid(cpf); 
            return true; 
        }catch(Exception e){ 
            throw new BadRequestException(String.format("Cpf inválido: %s", cpf));
        } 
    }
    
}
