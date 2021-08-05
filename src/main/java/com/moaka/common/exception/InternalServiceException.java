package com.moaka.common.exception;

public class InternalServiceException extends RuntimeException{
    private final int code;

    public int getCode() {
        return code;
    }

    public InternalServiceException(int code) {
        this.code = code;
    }

    public InternalServiceException(int code, String message) {
        super(message);
        this.code = code;
    }
}
