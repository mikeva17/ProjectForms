package com.example.reports.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BloqueReporte implements Serializable {

    private String uuid;
    private List<CriterioValor> criterios;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Set<ReporteBasico> basicos;
    private Set<ReporteAutomatico> automaticos;

    private static final long serialVersionUID = 1L;

    public BloqueReporte() {
        this.uuid = UUID.randomUUID().toString();
        this.criterios = new ArrayList<>();
        // Add at least one empty criteria by default
        this.criterios.add(new CriterioValor());
        this.fechaInicio = LocalDateTime.now();
        this.fechaFin = LocalDateTime.now();
        this.basicos = new HashSet<>();
        this.automaticos = new HashSet<>();
    }

    // Getters and Setters
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<CriterioValor> getCriterios() {
        return criterios;
    }

    public void setCriterios(List<CriterioValor> criterios) {
        this.criterios = criterios;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Set<ReporteBasico> getBasicos() {
        return basicos;
    }

    public void setBasicos(Set<ReporteBasico> basicos) {
        this.basicos = basicos;
    }

    public Set<ReporteAutomatico> getAutomaticos() {
        return automaticos;
    }

    public void setAutomaticos(Set<ReporteAutomatico> automaticos) {
        this.automaticos = automaticos;
    }

    public void agregarCriterio() {
        this.criterios.add(new CriterioValor());
    }

    public void eliminarCriterio(int index) {
        if (this.criterios.size() > 1) {
            this.criterios.remove(index);
        }
    }
}
