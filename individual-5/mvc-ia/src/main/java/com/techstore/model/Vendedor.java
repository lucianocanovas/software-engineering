package com.techstore.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * =============================================================================
 * CAPA MODELO / ORM: SUBCLASE Vendedor
 * =============================================================================
 * RELACIÓN UML: HERENCIA de Usuario.
 * Especialización que modela al colaborador comercial que atiende a clientes,
 * consulta stock de productos tecnológicos y registra ventas.
 */
@Entity
@DiscriminatorValue("VENDEDOR")
public class Vendedor extends Usuario {

    public Vendedor() {
        super();
    }

    public Vendedor(String nombreUsuario, String password, String nombre, String apellido, String email) {
        super(nombreUsuario, password, nombre, apellido, email);
    }

    @Override
    public String getRol() {
        return "VENDEDOR";
    }

    /**
     * Método propio de la especialización Vendedor definido en el diagrama:
     * registrarVenta()
     */
    public boolean registrarVenta() {
        // Habilita al vendedor a crear comprobantes comerciales y despachar mercadería
        return true;
    }
}

