package com.finflow.common.exception;

public class DuplicateCustomerException extends RuntimeException{
   public DuplicateCustomerException(String message){
       super(message);
   }
}
