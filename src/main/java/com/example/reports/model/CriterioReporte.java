package com.example.reports.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class CriterioReporte implements Serializable {

    private String uuid; // Unique ID for front-end manipulation if needed
    private TipoCriterio criterio;
    private String valor;
    private Set<ReporteBasico> basicos;
    private Set<ReporteAutomatico> automaticos;

    public CriterioReporte() {
        this.uuid = UUID.randomUUID().toString();
        this.basicos = new HashSet<>();
        this.automaticos = new HashSet<>();
    }

    // Getters and Setters
    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public TipoCriterio getCriterio() { return criterio; }
    public void setCriterio(TipoCriterio criterio) { this.criterio = criterio; }

    public String getValor() { return valor; }
    public void setValor(String valor) { this.valor = valor; }

    public Set<ReporteBasico> getBasicos() { return basicos; }
    public void setBasicos(Set<ReporteBasico> basicos) { this.basicos = basicos; }

    public Set<ReporteAutomatico> getAutomaticos() { return automaticos; }
    public void setAutomaticos(Set<ReporteAutomatico> automaticos) { this.automaticos = automaticos; }
    
    @Override
    public String toString() {
        return "Criterio: " + (criterio != null ? criterio.name() : "null") + ", Valor: " + valor;
    }
}
