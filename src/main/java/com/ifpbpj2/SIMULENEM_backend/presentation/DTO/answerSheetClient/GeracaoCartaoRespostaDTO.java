package com.ifpbpj2.SIMULENEM_backend.presentation.DTO.answerSheetClient;

import java.util.List;
import java.util.UUID;

public class GeracaoCartaoRespostaDTO{

    private UUID idProva;
    private String tituloSimulado;
    private int numeroTotalQuestoes;
    private int numeroAlternativas;
    private List<SectionAnswerSheet> secoes;


    public UUID getIdProva() {
        return idProva;
    }

    public void setIdProva(UUID idProva) {
        this.idProva = idProva;
    }

    public String getTituloSimulado() {
        return tituloSimulado;
    }

    public void setTituloSimulado(String tituloSimulado) {
        this.tituloSimulado = tituloSimulado;
    }

    public int getNumeroTotalQuestoes() {
        return numeroTotalQuestoes;
    }

    public void setNumeroTotalQuestoes(int numeroTotalQuestoes) {
        this.numeroTotalQuestoes = numeroTotalQuestoes;
    }

    public int getNumeroAlternativas() {
        return numeroAlternativas;
    }

    public void setNumeroAlternativas(int numeroAlternativas) {
        this.numeroAlternativas = numeroAlternativas;
    }

    public List<SectionAnswerSheet> getSecoes() {
        return secoes;
    }

    public void setSecoes(List<SectionAnswerSheet> secoes) {
        this.secoes = secoes;
    }
}
