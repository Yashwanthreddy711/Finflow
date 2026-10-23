package com.finflow.common.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
public class ApiErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private Map<String,String> errors;

    public ApiErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp=LocalDateTime.now();
    }
    public ApiErrorResponse(int status, String message,Map<String,String>errors) {
        this.status = status;
        this.message = message;
        this.timestamp=LocalDateTime.now();
        this.errors=errors;
    }
}
