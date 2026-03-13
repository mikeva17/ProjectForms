package com.example.reports.view;

import com.example.reports.model.BloqueReporte;
import com.example.reports.model.CriterioValor;
import com.example.reports.model.ReporteAutomatico;
import com.example.reports.model.ReporteBasico;
import com.example.reports.model.SolicitudReportes;
import com.example.reports.model.TipoCriterio;
import com.example.reports.repository.SolicitudReportesRepository;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Named
@ViewScoped
public class ReporteFormBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SolicitudReportesRepository repository;

    private List<BloqueReporte> listaCriterios;

    // Pattern for simple email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @PostConstruct
    public void init() {
        limpiar(); // Initialize with one empty block
    }

    public void agregarBloque() {
        listaCriterios.add(new BloqueReporte());
    }

    public void eliminarBloque(int index) {
        if (listaCriterios.size() > 1) {
            listaCriterios.remove(index);
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso",
                            "Debe existir al menos un bloque de criterios."));
        }
    }

    public void agregarCriterio(BloqueReporte bloque) {
        bloque.agregarCriterio();
    }

    public void eliminarCriterio(BloqueReporte bloque, int index) {
        bloque.eliminarCriterio(index);
        evaluarAutomaticos(bloque);
    }

    public void limpiar() {
        listaCriterios = new ArrayList<>();
        listaCriterios.add(new BloqueReporte());
    }

    public void guardar() {
        // Validate
        for (int i = 0; i < listaCriterios.size(); i++) {
            BloqueReporte b = listaCriterios.get(i);
            int blockNum = i + 1;

            // Validate dates
            /*
             * Requirement:
             * "periodo de busqueda (1 sola vez por bloque) (fecha inicio y fecha fin)"
             * Assuming required.
             */
            if (b.getFechaInicio() == null || b.getFechaFin() == null) {
                error("Bloque " + blockNum + ": Debe especificar Fecha Inicio y Fecha Fin.");
                return;
            }
            if (b.getFechaInicio().isAfter(b.getFechaFin())) {
                error("Bloque " + blockNum + ": La Fecha Inicio no puede ser posterior a la Fecha Fin.");
                return;
            }

            // Validate inner criteria
            List<CriterioValor> criterios = b.getCriterios();
            for (int j = 0; j < criterios.size(); j++) {
                CriterioValor c = criterios.get(j);
                int critNum = j + 1;

                if (c.getCriterio() == null) {
                    error("Bloque " + blockNum + ", Criterio " + critNum + ": Debe seleccionar un Tipo de Criterio.");
                    return;
                }
                if (c.getValor() == null || c.getValor().trim().isEmpty()) {
                    error("Bloque " + blockNum + ", Criterio " + critNum + ": El Valor no puede estar vacío.");
                    return;
                }

                // Email validation
                if (c.getCriterio() == TipoCriterio.EMAIL) {
                    if (!EMAIL_PATTERN.matcher(c.getValor()).matches()) {
                        error("Bloque " + blockNum + ", Criterio " + critNum + ": Formato de correo inválido.");
                        return;
                    }
                }
            }

            if ((b.getBasicos() == null || b.getBasicos().isEmpty()) &&
                    (b.getAutomaticos() == null || b.getAutomaticos().isEmpty())) {
                error("Bloque " + blockNum + ": seleccione al menos un reporte (Básico o Automático).");
                return;
            }
        }

        // Persist
        SolicitudReportes s = new SolicitudReportes(new ArrayList<>(listaCriterios));
        repository.save(s);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Solicitud Guardada OK. ID: " + s.getId()));
    }

    // Listener for criterion change (Ajax)
    public void onCriterioChange(BloqueReporte bloque, CriterioValor c) {
        c.setValor(""); // Clear value on type change
        evaluarAutomaticos(bloque);
    }

    private void evaluarAutomaticos(BloqueReporte bloque) {
        if (!bloque.isAutomaticosHabilitados()) {
            bloque.getAutomaticos().clear();
        }
    }

    // Helpers
    private void error(String msg) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", msg));
    }

    // Getters for Enums (for p:selectItems)
    public List<TipoCriterio> getTiposCriterio() {
        return Arrays.asList(TipoCriterio.values());
    }

    public List<ReporteBasico> getReportesBasicos() {
        return Arrays.asList(ReporteBasico.values());
    }

    public List<ReporteAutomatico> getReportesAutomaticos() {
        return Arrays.asList(ReporteAutomatico.values());
    }

    public int getIndiceGlobalCriterio(CriterioValor target) {
        int index = 1;
        for (BloqueReporte bloque : listaCriterios) {
            for (CriterioValor c : bloque.getCriterios()) {
                if (c == target) {
                    return index;
                }
                index++;
            }
        }
        return index;
    }

    public List<BloqueReporte> getListaCriterios() {
        return listaCriterios;
    }

    public void setListaCriterios(List<BloqueReporte> listaCriterios) {
        this.listaCriterios = listaCriterios;
    }
}
