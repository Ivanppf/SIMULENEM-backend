package com.ifpbpj2.SIMULENEM_backend.exception;

public class EntityInUseException extends RuntimeException {

    public EntityInUseException(String message) {
        super(message);
    }
}
