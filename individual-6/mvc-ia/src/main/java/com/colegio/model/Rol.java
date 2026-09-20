package com.colegio.model;

/**
 * Enumeración que define los roles de seguridad en la aplicación para Spring Security.
 */
public enum Rol {
    ROLE_DOCENTE("Docente"),
    ROLE_ADMIN("Administrador"),
    ROLE_ALUMNO("Alumno");

    private final String etiqueta;

    Rol(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}

