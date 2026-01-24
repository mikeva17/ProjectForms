package com.example.reports.model;

public enum ReporteBasico {
    REPORTE_1("Reporte 1"),
    REPORTE_2("Reporte 2"),
    REPORTE_3("Reporte 3"),
    REPORTE_4("Reporte 4"),
    REPORTE_5("Reporte 5"),
    REPORTE_6("Reporte 6");

    private final String label;

    ReporteBasico(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
