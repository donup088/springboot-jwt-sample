package com.token.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {
    private HttpStatus httpStatus;
    private String error;
    private String message;

    public ApiError(HttpStatus httpStatus, String error, String message) {
        super();
        this.httpStatus = httpStatus;
        this.error = error;
        this.message = message;
    }

}
