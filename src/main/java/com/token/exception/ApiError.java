package com.token.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {
    private HttpStatus httpStatus;
    private String error;
    private String message;

    public ApiError() {
    }

    public ApiError(HttpStatus httpStatus, String error, String message) {
        super();
        this.httpStatus = httpStatus;
        this.error = error;
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
