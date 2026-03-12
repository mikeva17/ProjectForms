package com.example.reports.repository;

import com.example.reports.model.SolicitudReportes;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class InMemorySolicitudReportesRepository implements SolicitudReportesRepository {

    // Thread-safe list for simulation
    private final List<SolicitudReportes> db = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void save(SolicitudReportes s) {
        db.add(s);
        System.out.println("Guardando Solicitud: " + s.getId());

        try {
            String home = System.getProperty("user.home");
            java.nio.file.Path path = java.nio.file.Paths.get(home, "reportes_guardados.txt");

            StringBuilder sb = new StringBuilder();
            sb.append("\n==========================================\n");
            sb.append("ID SOLICITUD: ").append(s.getId()).append("\n");
            sb.append("FECHA: ").append(s.getFecha()).append("\n");
            sb.append("CRITERIOS:\n");

            for (com.example.reports.model.BloqueReporte b : s.getCriterios()) {
                sb.append("  - BLOQUE:\n");
                sb.append("    Fecha Inicio: ").append(b.getFechaInicio()).append(", Fecha Fin: ")
                        .append(b.getFechaFin()).append("\n");
                sb.append("    Criterios:\n");
                for (com.example.reports.model.CriterioValor cv : b.getCriterios()) {
                    sb.append("      * ").append(cv.getCriterio()).append(": ").append(cv.getValor()).append("\n");
                }
                sb.append("    Reportes Estandar: ").append(b.getBasicos()).append("\n");
                sb.append("    Reportes Automáticos: ").append(b.getAutomaticos()).append("\n");
                sb.append("    --------------------------------------\n");
            }
            sb.append("==========================================\n");

            java.nio.file.Files.write(path, sb.toString().getBytes(), java.nio.file.StandardOpenOption.CREATE,
                    java.nio.file.StandardOpenOption.APPEND);
            System.out.println("-- SOL --");
            System.out.println(sb.toString());
            System.out.println("-- SOL --");
            System.out.println("Guardado en archivo: " + path);

        } catch (java.io.IOException e) {
            e.printStackTrace();
            System.err.println("Error al escribir archivo: " + e.getMessage());
        }
    }

    @Override
    public List<SolicitudReportes> findAll() {
        return new ArrayList<>(db);
    }
}
