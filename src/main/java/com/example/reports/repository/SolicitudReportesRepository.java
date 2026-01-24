package com.example.reports.repository;

import com.example.reports.model.SolicitudReportes;
import java.util.List;

public interface SolicitudReportesRepository {
    void save(SolicitudReportes s);

    List<SolicitudReportes> findAll();
}
