package com.clubdeportivo.entity.enums;

/**
 * =========================================================================================
 * ENUM: EstadoPuntoAcceso
 * =========================================================================================
 * Representa la condición operativa de un punto de control físico (torniquete, molinete, portón).
 */
public enum EstadoPuntoAcceso {
    OPERATIVO("Molinete / Puerta en funcionamiento normal"),
    EN_MANTENIMIENTO("Dispositivo temporalmente fuera de servicio por mantenimiento"),
    INACTIVO("Punto de acceso clausurado o deshabilitado");

    private final String descripcion;

    EstadoPuntoAcceso(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
