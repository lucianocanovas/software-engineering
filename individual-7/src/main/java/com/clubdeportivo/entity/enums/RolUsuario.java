package com.clubdeportivo.entity.enums;

/**
 * =========================================================================================
 * ENUM: RolUsuario
 * =========================================================================================
 * Perfiles de seguridad RBAC (Role-Based Access Control) para Spring Security:
 * - ROLE_ADMIN: Administrador con control total del sistema, configuración, auditoría y tesorería.
 * - ROLE_OPERADOR: Personal de recepción y control de acceso (registra entradas/salidas y cobros).
 * - ROLE_SOCIO: Miembro del club con acceso a su portal familiar, estado de cuotas y recibos.
 */
public enum RolUsuario {
    ROLE_ADMIN("Administrador del Sistema"),
    ROLE_OPERADOR("Operador de Recepción / Caja"),
    ROLE_SOCIO("Socio Titular del Club");

    private final String descripcion;

    RolUsuario(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
