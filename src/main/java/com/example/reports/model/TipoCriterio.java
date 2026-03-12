package com.example.reports.model;

public enum TipoCriterio {
    EMAIL("Correo Electrónico", true),
    NOMBRE("Nombre Completo", false);

    private final String label;
    private final boolean permiteAutomaticos;

    TipoCriterio(String label, boolean permiteAutomaticos) {
        this.label = label;
        this.permiteAutomaticos = permiteAutomaticos;
    }

    public String getLabel() {
        return label;
    }

    public boolean isPermiteAutomaticos() {
        return permiteAutomaticos;
    }
}
