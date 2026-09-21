package com.clubdeportivo.entity.enums;

/**
 * =========================================================================================
 * ENUM: Parentesco
 * =========================================================================================
 * Define la relación vincular o grado de afinidad del Familiar Adherente con el Socio Titular.
 * Requerido para verificar beneficios en cuotas familiares y requisitos de minoridad.
 */
public enum Parentesco {
    CONYUGE("Cónyuge o Pareja Conviviente"),
    HIJO("Hijo"),
    HIJA("Hija"),
    PADRE("Padre"),
    MADRE("Madre"),
    HERMANO("Hermano"),
    HERMANA("Hermana"),
    OTRO("Otro Vínculo Familiar Legal");

    private final String descripcion;

    Parentesco(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
