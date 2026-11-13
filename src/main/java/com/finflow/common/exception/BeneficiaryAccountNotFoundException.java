package com.finflow.common.exception;

public class BeneficiaryAccountNotFoundException extends RuntimeException{
    public BeneficiaryAccountNotFoundException(String message){
        super(message);
    }
}
