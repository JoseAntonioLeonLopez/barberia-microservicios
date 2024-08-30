package com.barber.userservice.exception;

@SuppressWarnings("serial")
public class RoleAlreadyExistsException extends RuntimeException {
    public RoleAlreadyExistsException(String message) {
        super(message);
    }
}
