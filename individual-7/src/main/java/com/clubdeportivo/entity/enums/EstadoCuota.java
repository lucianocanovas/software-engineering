package com.clubdeportivo.entity.enums;

/**
 * =========================================================================================
 * ENUM: EstadoCuota
 * =========================================================================================
 * Estados del ciclo de vida financiero de una cuota social familiar mensual.
 */
public enum EstadoCuota {
    PENDIENTE("Cuota emitida pendiente de pago dentro del término ordinario"),
    PAGADA("Cuota cancelada en su totalidad con recibo generado"),
    VENCIDA("Plazo de vencimiento superado con recargos por mora acumulados"),
    ANULADA("Cuota dejada sin efecto por resolución administrativa o baja de membrecía");

    private final String descripcion;

    EstadoCuota(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
