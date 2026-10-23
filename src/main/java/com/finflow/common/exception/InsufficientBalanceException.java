package com.finflow.common.exception;

public class InsufficientBalanceException extends RuntimeException{
    public InsufficientBalanceException(String e){
        super(e);
    }
}
