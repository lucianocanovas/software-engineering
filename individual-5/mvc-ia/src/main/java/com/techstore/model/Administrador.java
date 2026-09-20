package com.techstore.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * =============================================================================
 * CAPA MODELO / ORM: SUBCLASE Administrador
 * =============================================================================
 * RELACIÓN UML: HERENCIA de Usuario.
 * Especialización que representa al rol con permisos globales sobre la plataforma,
 * configuración de parámetros, gestión de cuentas y auditoría.
 */
@Entity
@DiscriminatorValue("ADMIN")
public class Administrador extends Usuario {

    public Administrador() {
        super();
    }

    public Administrador(String nombreUsuario, String password, String nombre, String apellido, String email) {
        super(nombreUsuario, password, nombre, apellido, email);
    }

    @Override
    public String getRol() {
        return "ADMIN";
    }

    /**
     * Método de negocio propio de la especialización Administrador
     * especificado en el diagrama de clases: gestionarUsuarios()
     */
    public boolean gestionarUsuarios() {
        // En una aplicación empresarial, encapsula la capacidad de alta, baja
        // y modificación de usuarios y asignación de permisos.
        return true;
    }
}

