package com.ifpbpj2.SIMULENEM_backend.exception;

import java.util.UUID;

public class SectionNotFoundException extends RuntimeException {

    public SectionNotFoundException(UUID id) {
        super("Seção com o UUID:" + id + " não encontrada");
    }

    public SectionNotFoundException(String message) {
        super(message);
    }
}
