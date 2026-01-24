package com.example.reports.model;

public enum TipoCriterio {
    EMAIL("Correo Electrónico"),
    NOMBRE("Nombre Completo");

    private final String label;

    TipoCriterio(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
