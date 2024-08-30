package com.barber.userservice.exception;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends RuntimeException {
    private HttpStatus status;

    public InvalidInputException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
