package com.clubdeportivo.dto.request;

import jakarta.validation.constraints.NotNull;

/**
 * =========================================================================================
 * DTO REQUEST: GrupoFamiliarRequestDTO
 * =========================================================================================
 * Datos para la constitución o edición de un Grupo Familiar encabezado por un Socio Titular.
 */
public class GrupoFamiliarRequestDTO {

    private Long id;

    @NotNull(message = "Debe asignar un socio titular al grupo familiar")
    private Long socioTitularId;

    private String observaciones;

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSocioTitularId() {
        return socioTitularId;
    }

    public void setSocioTitularId(Long socioTitularId) {
        this.socioTitularId = socioTitularId;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
