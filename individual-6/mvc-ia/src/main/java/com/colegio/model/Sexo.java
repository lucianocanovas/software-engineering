package com.colegio.model;

/**
 * Enumeración que representa las opciones de Sexo para el registro de docentes
 * según los requerimientos explícitos del ejercicio ("Nombre", "Apellido", "Sexo" y "Fecha de Nacimiento").
 */
public enum Sexo {
    MASCULINO("Masculino"),
    FEMENINO("Femenino"),
    OTRO("Otro");

    private final String descripcion;

    Sexo(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

