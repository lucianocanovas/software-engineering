package com.techstore.model;

/**
 * =============================================================================
 * CAPA MODELO - ENUMERACIÓN DE DOMINIO: EstadoOrden
 * =============================================================================
 * Define los estados del ciclo de vida por los que transita una Orden de Compra:
 * - PENDIENTE: La orden ha sido emitida al proveedor mayorista y espera entrega.
 * - RECIBIDA: La mercadería llegó a depósito, se confirmó la recepción física
 *             y el stock de los productos tecnológicos fue actualizado.
 * - CANCELADA: La orden fue dada de baja antes de su recepción.
 */
public enum EstadoOrden {
    PENDIENTE("Pendiente de Recepción", "warning"),
    RECIBIDA("Recibida y Stock Actualizado", "success"),
    CANCELADA("Cancelada", "danger");

    private final String descripcion;
    private final String badgeColor;

    EstadoOrden(String descripcion, String badgeColor) {
        this.descripcion = descripcion;
        this.badgeColor = badgeColor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getBadgeColor() {
        return badgeColor;
    }
}

