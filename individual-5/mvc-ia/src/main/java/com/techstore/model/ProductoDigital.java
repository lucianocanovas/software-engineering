package com.techstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.util.UUID;

/**
 * =============================================================================
 * CAPA MODELO / ORM: SUBCLASE ProductoDigital
 * =============================================================================
 * RELACIÓN UML: HERENCIA de Producto.
 * Especialización que modela software, suscripciones en la nube, licencias de
 * sistemas operativos y antivirus que no ocupan espacio físico ni requieren depósito,
 * sino claves de activación y enlaces de descarga seguros.
 */
@Entity
@DiscriminatorValue("DIGITAL")
public class ProductoDigital extends Producto {

    @Column(name = "clave_licencia", length = 100, nullable = true)
    private String claveLicencia;

    @Column(name = "url_descarga", length = 255, nullable = true)
    private String urlDescarga;

    @Column(name = "vigencia_dias", nullable = true)
    private Integer vigenciaDias = 365;

    public ProductoDigital() {
        super();
    }

    public ProductoDigital(String codigo, String nombre, String descripcion, Double precioVenta,
                           Categoria categoria, String claveLicencia, String urlDescarga, Integer vigenciaDias) {
        super(codigo, nombre, descripcion, precioVenta, categoria);
        this.claveLicencia = claveLicencia != null ? claveLicencia : generarClaveLicencia();
        this.urlDescarga = urlDescarga;
        this.vigenciaDias = vigenciaDias != null ? vigenciaDias : 365;
    }

    /**
     * Implementación del método polimórfico calcularPrecioFinal():
     * Para productos digitales se aplica un 5% de descuento por entrega digital
     * y ausencia de costos logísticos de almacenamiento.
     */
    @Override
    public Double calcularPrecioFinal() {
        if (getPrecioVenta() == null) return 0.0;
        double descuentoDigital = getPrecioVenta() * 0.05;
        return Math.round((getPrecioVenta() - descuentoDigital) * 100.0) / 100.0;
    }

    /**
     * Generación automática de clave de licencia alfanumérica en formato UUID
     * especificada en el diagrama de clases: generarClaveLicencia()
     */
    public String generarClaveLicencia() {
        String key = UUID.randomUUID().toString().toUpperCase();
        this.claveLicencia = "TECH-" + key.substring(0, 8) + "-" + key.substring(9, 13) + "-" + key.substring(14, 18);
        return this.claveLicencia;
    }

    @Override
    public String getTipoProducto() {
        return "DIGITAL";
    }

    @Override
    public boolean esFisico() {
        return false;
    }

    // --- Getters y Setters ---

    public String getClaveLicencia() {
        return claveLicencia;
    }

    public void setClaveLicencia(String claveLicencia) {
        this.claveLicencia = claveLicencia;
    }

    public String getUrlDescarga() {
        return urlDescarga;
    }

    public void setUrlDescarga(String urlDescarga) {
        this.urlDescarga = urlDescarga;
    }

    public Integer getVigenciaDias() {
        return vigenciaDias;
    }

    public void setVigenciaDias(Integer vigenciaDias) {
        this.vigenciaDias = vigenciaDias;
    }
}

