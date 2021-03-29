package com.token.exception;

public class JwtCustomException extends RuntimeException{
    public JwtCustomException() {
    }

    public JwtCustomException(String message) {
        super(message);
    }

    public JwtCustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtCustomException(Throwable cause) {
        super(cause);
    }

    public JwtCustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
