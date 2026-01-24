package com.example.reports.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class SolicitudReportes implements Serializable {
    private UUID id;
    private LocalDateTime fecha;
    private List<CriterioReporte> criterios;

    public SolicitudReportes() {}

    public SolicitudReportes(List<CriterioReporte> criterios) {
        this.id = UUID.randomUUID();
        this.fecha = LocalDateTime.now();
        this.criterios = criterios;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public List<CriterioReporte> getCriterios() { return criterios; }
    public void setCriterios(List<CriterioReporte> criterios) { this.criterios = criterios; }
}
