package com.finflow.common.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String e){
        super(e);
    }
}
