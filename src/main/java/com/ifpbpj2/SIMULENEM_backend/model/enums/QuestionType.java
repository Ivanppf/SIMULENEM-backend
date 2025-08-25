package com.ifpbpj2.SIMULENEM_backend.model.enums;

public enum QuestionType {
    ABERTA(1),
    FECHADA(2);

    private final int value;

    private QuestionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static QuestionType fromInt(int value) {
        for (QuestionType type : QuestionType.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido para QuestionType: " + value);
    }
}