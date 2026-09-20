package com.techstore.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * =============================================================================
 * CAPA MODELO / ORM: SUBCLASE EncargadoCompras
 * =============================================================================
 * RELACIÓN UML: HERENCIA de Usuario.
 * Especialización que modela al responsable del departamento de compras de la empresa.
 * Se encarga de gestionar proveedores, emitir órdenes de compra mayoristas y
 * recibir mercadería física para actualizar el stock tecnológico en depósito.
 */
@Entity
@DiscriminatorValue("COMPRAS")
public class EncargadoCompras extends Usuario {

    public EncargadoCompras() {
        super();
    }

    public EncargadoCompras(String nombreUsuario, String password, String nombre, String apellido, String email) {
        super(nombreUsuario, password, nombre, apellido, email);
    }

    @Override
    public String getRol() {
        return "COMPRAS";
    }

    /**
     * Método propio de la especialización EncargadoCompras: generarOrdenCompra()
     */
    public boolean generarOrdenCompra() {
        return true;
    }

    /**
     * Método propio de la especialización EncargadoCompras: recibirMercaderia()
     * Permite confirmar la recepción de una orden de compra, impactando el inventario.
     */
    public boolean recibirMercaderia() {
        return true;
    }
}

