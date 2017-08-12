package com.xxytech.tracker.exception;

public class TrackerRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 3960592581471439923L;

    private String code;

    public TrackerRuntimeException() {
        new TrackerRuntimeException(null);
    }

    public TrackerRuntimeException(String code) {
        super();
        setCode(code);
    }

    public TrackerRuntimeException(String code, String message) {
        super(message);
        setCode(code);
    }

    public TrackerRuntimeException(String code, String message, Throwable cause) {
        super(message, cause);
        setCode(code);
    }

    public TrackerRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
