package com.finflow.common.exception;

public class CustomerHasAccountsException extends RuntimeException{
    public CustomerHasAccountsException(String message){
        super(message);
    }
}
