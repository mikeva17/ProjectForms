package com.example.reports.model;

import java.io.Serializable;
import java.util.UUID;

public class CriterioValor implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;
    private TipoCriterio criterio;
    private String valor;

    public CriterioValor() {
        this.uuid = UUID.randomUUID().toString();
    }

    public CriterioValor(TipoCriterio criterio, String valor) {
        this();
        this.criterio = criterio;
        this.valor = valor;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public TipoCriterio getCriterio() {
        return criterio;
    }

    public void setCriterio(TipoCriterio criterio) {
        this.criterio = criterio;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
