package com.ifpbpj2.SIMULENEM_backend.model.enums;

public enum Origin {
    AREA_CONHECIMENTO_ENEM(1),
    DISCIPLINA(2),
    ASSUNTO(3);

    private final int value;

    private Origin(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Origin fromInt(int value) {
        for (Origin type : Origin.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Valor inválido para QuestionType: " + value);
    }

}
