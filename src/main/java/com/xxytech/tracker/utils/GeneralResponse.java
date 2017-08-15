package com.xxytech.tracker.utils;

public class GeneralResponse {

    private int             status;
    private String          message;
    private String          code;

    public GeneralResponse() {
    }
    
    public GeneralResponse(String code, String message, int status) {
        super();
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public int getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
