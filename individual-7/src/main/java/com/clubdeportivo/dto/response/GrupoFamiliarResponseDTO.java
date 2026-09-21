package com.clubdeportivo.dto.response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================================
 * DTO RESPONSE: GrupoFamiliarResponseDTO
 * =========================================================================================
 * Representa la agregación completa de la suscripción familiar, incluyendo miembros e integrantes.
 */
public class GrupoFamiliarResponseDTO {

    private Long id;
    private String codigo;
    private LocalDate fechaCreacion;
    private String observaciones;
    private Long titularId;
    private String titularNombre;
    private String titularNumeroSocio;
    private String titularDni;
    private boolean titularAlDia;
    private int cantidadTotalIntegrantes;
    private List<FamiliarAdherenteResponseDTO> adherentes = new ArrayList<>();

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Long getTitularId() {
        return titularId;
    }

    public void setTitularId(Long titularId) {
        this.titularId = titularId;
    }

    public String getTitularNombre() {
        return titularNombre;
    }

    public void setTitularNombre(String titularNombre) {
        this.titularNombre = titularNombre;
    }

    public String getTitularNumeroSocio() {
        return titularNumeroSocio;
    }

    public void setTitularNumeroSocio(String titularNumeroSocio) {
        this.titularNumeroSocio = titularNumeroSocio;
    }

    public String getTitularDni() {
        return titularDni;
    }

    public void setTitularDni(String titularDni) {
        this.titularDni = titularDni;
    }

    public boolean isTitularAlDia() {
        return titularAlDia;
    }

    public void setTitularAlDia(boolean titularAlDia) {
        this.titularAlDia = titularAlDia;
    }

    public int getCantidadTotalIntegrantes() {
        return cantidadTotalIntegrantes;
    }

    public void setCantidadTotalIntegrantes(int cantidadTotalIntegrantes) {
        this.cantidadTotalIntegrantes = cantidadTotalIntegrantes;
    }

    public List<FamiliarAdherenteResponseDTO> getAdherentes() {
        return adherentes;
    }

    public void setAdherentes(List<FamiliarAdherenteResponseDTO> adherentes) {
        this.adherentes = adherentes;
    }
}
