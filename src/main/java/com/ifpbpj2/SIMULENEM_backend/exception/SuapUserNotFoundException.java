package com.ifpbpj2.SIMULENEM_backend.exception;

public class SuapUserNotFoundException extends RuntimeException {
        public SuapUserNotFoundException() {
                super("Suap user not found");
        }
}
