package com.example.reports.view;

import com.example.reports.model.CriterioReporte;
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

    @Inject
    private SolicitudReportesRepository repository;

    private List<CriterioReporte> listaCriterios;

    // Pattern for simple email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @PostConstruct
    public void init() {
        limpiar(); // Initialize with one empty block
    }

    public void agregarBloque() {
        listaCriterios.add(new CriterioReporte());
    }

    public void eliminarBloque(int index) {
        if (listaCriterios.size() > 1) {
            listaCriterios.remove(index);
        } else {
            // Optional: Show message that at least one block is required, though logic
            // usually prevents removing the last one UI-wise or immediately re-adds.
            // Requirement says "minimo dejar 1 bloque", so if they try to remove the last
            // one, we can either block it or just clear it.
            // Let's just clear it if size is 1, essentially acting like reset for that
            // block, or show error.
            // Better UX: Don't show delete button for the only block, or just re-init.
            // Let's adhere strictly: "Minimum leave 1 block".
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Debe existir al menos un criterio."));
        }
    }

    public void limpiar() {
        listaCriterios = new ArrayList<>();
        listaCriterios.add(new CriterioReporte());
    }

    public void guardar() {
        // Validate
        for (int i = 0; i < listaCriterios.size(); i++) {
            CriterioReporte c = listaCriterios.get(i);
            int lineNum = i + 1;

            if (c.getCriterio() == null) {
                error("Bloque " + lineNum + ": Debe seleccionar un Criterio.");
                return;
            }
            if (c.getValor() == null || c.getValor().trim().isEmpty()) {
                error("Bloque " + lineNum + ": El Valor no puede estar vacío.");
                return;
            }

            // Email validation
            if (c.getCriterio() == TipoCriterio.EMAIL) {
                if (!EMAIL_PATTERN.matcher(c.getValor()).matches()) {
                    error("Bloque " + lineNum + ": Formato de correo inválido.");
                    return;
                }
            }

            if ((c.getBasicos() == null || c.getBasicos().isEmpty()) &&
                    (c.getAutomaticos() == null || c.getAutomaticos().isEmpty())) {
                error("Bloque " + lineNum + ": seleccione al menos un reporte (Básico o Automático).");
                return;
            }
        }

        // Persist
        SolicitudReportes s = new SolicitudReportes(new ArrayList<>(listaCriterios));
        repository.save(s);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Solicitud Guardada OK. ID: " + s.getId()));

        // Requirement: Show summary on screen.
        // We can just keep the form as is (showing what was saved) or perhaps show a
        // dialog.
        // The requirement says "Mostrar en pantalla un resumen... y/o JSON".
        // Since the form holds the state, simply showing a success message matches
        // "guardado OK".
        // We could log the JSON to console or show it in a dialog.
    }

    // Listener for criterion change (Ajax)
    public void onCriterioChange(int index) {
        CriterioReporte c = listaCriterios.get(index);
        c.setValor(""); // Clear value on type change to avoid confusion (e.g. name in email field)
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

    public List<CriterioReporte> getListaCriterios() {
        return listaCriterios;
    }

    public void setListaCriterios(List<CriterioReporte> listaCriterios) {
        this.listaCriterios = listaCriterios;
    }
}
