package com.ifpbpj2.SIMULENEM_backend.business.services.exceptions;

import java.util.UUID;

public class CategoryNotFoundException extends RuntimeException {


    public CategoryNotFoundException(UUID id) {
        super("Categoria com id " + id + " não encontrada");
    }

    public CategoryNotFoundException(String mensage) {
        super(mensage);
    }
}
