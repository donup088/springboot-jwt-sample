package com.token.exception;

public class CUserNotFoundException extends RuntimeException{
    public CUserNotFoundException() {
        super();
    }

    public CUserNotFoundException(String message) {
        super(message);
    }

    public CUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CUserNotFoundException(Throwable cause) {
        super(cause);
    }

    protected CUserNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
