package com.ifpbpj2.SIMULENEM_backend.model.enums;

public enum Difficulty {
    FACIL(1),
    MEDIO(2),
    DIFICIL(3);

    private final int value;

    private Difficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Difficulty fromInt(int value) {
        for (Difficulty type : Difficulty.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido para QuestionType: " + value);
    }



}
