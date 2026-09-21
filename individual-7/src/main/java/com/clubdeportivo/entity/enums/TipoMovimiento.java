package com.clubdeportivo.entity.enums;

/**
 * =========================================================================================
 * ENUM: TipoMovimiento
 * =========================================================================================
 * Representa el sentido del flujo de personas registrado en los puntos de acceso del club.
 */
public enum TipoMovimiento {
    ENTRADA("Ingreso a las instalaciones"),
    SALIDA("Egreso de las instalaciones");

    private final String descripcion;

    TipoMovimiento(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
