/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ecamping;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author ramon
 */
public class ValidatorTent implements ConstraintValidator<ValidateTent, String>{
    
    private List<String> tents;
    
    @Override
    public void initialize(ValidateTent validateTent){
        this.tents = new ArrayList<>();
        this.tents.add("INDIVIDUAL");
        this.tents.add("DUPLEX");
    }
    
    @Override
    public boolean isValid(String valor, ConstraintValidatorContext context) {
        return valor == null ? false : tents.contains(valor);
    }
    
}
