package com.clubdeportivo.entity.enums;

/**
 * =========================================================================================
 * ENUM: TipoMedioPago
 * =========================================================================================
 * Clasifica las modalidades de abono admitidas por la tesorería del club deportivo:
 * 1. Efectivo: Cobro en ventanilla con posible bonificación inmediata.
 * 2. Transferencia: Pago electrónico interbancario por CBU/CVU con número de comprobante.
 * 3. Mercado Pago: Pasarela fintech con QR digital, link de pago o checkout web.
 */
public enum TipoMedioPago {
    EFECTIVO("Efectivo en Ventanilla / Caja"),
    TRANSFERENCIA("Transferencia Bancaria (CBU / CVU)"),
    MERCADO_PAGO("Mercado Pago (QR / Pasarela Digital)");

    private final String descripcion;

    TipoMedioPago(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
