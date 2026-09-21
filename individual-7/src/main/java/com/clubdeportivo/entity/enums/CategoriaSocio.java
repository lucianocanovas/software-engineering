package com.clubdeportivo.entity.enums;

/**
 * =========================================================================================
 * ENUM: CategoriaSocio
 * =========================================================================================
 * Clasifica a los socios del club deportivo según su nivel de membrecía y rango etario.
 * Determina derechos de voto, acceso a instalaciones preferenciales y esquemas arancelarios.
 */
public enum CategoriaSocio {
    ACTIVO("Socio Activo Pleno - Acceso total"),
    CADETE("Socio Cadete (Menor de 18 años)"),
    VITALICIO("Socio Vitalicio (Más de 30 años de antigüedad)"),
    HONORARIO("Socio Honorario - Distinción institucional");

    private final String descripcion;

    CategoriaSocio(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
