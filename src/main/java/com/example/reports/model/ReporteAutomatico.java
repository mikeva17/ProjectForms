package com.example.reports.model;

public enum ReporteAutomatico {
    ACTO_1("Automático 1"),
    ACTO_2("Automático 2"),
    ACTO_3("Automático 3"),
    ACTO_4("Automático 4");

    private final String label;

    ReporteAutomatico(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
