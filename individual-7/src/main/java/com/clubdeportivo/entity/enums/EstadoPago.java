package com.clubdeportivo.entity.enums;

/**
 * =========================================================================================
 * ENUM: EstadoPago
 * =========================================================================================
 * Representa el resultado transaccional de un intento de cobro o pago registrado.
 */
public enum EstadoPago {
    COMPLETADO("Pago acreditado exitosamente"),
    PENDIENTE_VERIFICACION("Pago pendiente de conciliación bancaria o confirmación externa"),
    RECHAZADO("Pago denegado por fondos insuficientes o inconsistencias"),
    REEMBOLSADO("Monto reintegrado al pagador");

    private final String descripcion;

    EstadoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
