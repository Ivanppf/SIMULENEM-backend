package com.ifpbpj2.SIMULENEM_backend.business.services.exceptions;

public class EntityInUseException extends RuntimeException{

    public EntityInUseException(String message) {
        super(message);
    }
}
