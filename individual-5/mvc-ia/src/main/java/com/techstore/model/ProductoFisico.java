package com.techstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * =============================================================================
 * CAPA MODELO / ORM: SUBCLASE ProductoFisico
 * =============================================================================
 * RELACIÓN UML: HERENCIA de Producto.
 * Especialización que representa dispositivos de hardware (laptops, placas de video,
 * monitores, discos, etc.) que ocupan espacio físico, poseen stock almacenable
 * y requieren seguimiento de ubicación en bodega.
 *
 * Su stock es el que se actualiza directamente a través de las órdenes de compra.
 */
@Entity
@DiscriminatorValue("FISICO")
public class ProductoFisico extends Producto {

    @Column(name = "stock", nullable = true)
    private Integer stock = 0;

    @Column(name = "stock_minimo", nullable = true)
    private Integer stockMinimo = 5;

    @Column(name = "peso", nullable = true)
    private Double peso = 0.0;

    @Column(name = "ubicacion_deposito", length = 100, nullable = true)
    private String ubicacionDeposito;

    public ProductoFisico() {
        super();
    }

    public ProductoFisico(String codigo, String nombre, String descripcion, Double precioVenta,
                          Categoria categoria, Integer stock, Integer stockMinimo,
                          Double peso, String ubicacionDeposito) {
        super(codigo, nombre, descripcion, precioVenta, categoria);
        this.stock = stock != null ? stock : 0;
        this.stockMinimo = stockMinimo != null ? stockMinimo : 5;
        this.peso = peso != null ? peso : 0.0;
        this.ubicacionDeposito = ubicacionDeposito;
    }

    /**
     * Implementación del método polimórfico calcularPrecioFinal():
     * Para productos físicos, calcula un recargo del 3% por manipulación y seguro de embalaje.
     */
    @Override
    public Double calcularPrecioFinal() {
        if (getPrecioVenta() == null) return 0.0;
        double seguroEmbalaje = getPrecioVenta() * 0.03;
        return Math.round((getPrecioVenta() + seguroEmbalaje) * 100.0) / 100.0;
    }

    @Override
    public String getTipoProducto() {
        return "FISICO";
    }

    @Override
    public boolean esFisico() {
        return true;
    }

    /**
     * Actualiza el inventario físico al recibir mercadería de una Orden de Compra.
     */
    public void incrementarStock(int cantidad) {
        if (cantidad > 0) {
            this.stock = (this.stock != null ? this.stock : 0) + cantidad;
        }
    }

    /**
     * Decrementa el stock al registrar una venta comercial.
     */
    public boolean decrementarStock(int cantidad) {
        if (cantidad > 0 && this.stock != null && this.stock >= cantidad) {
            this.stock -= cantidad;
            return true;
        }
        return false;
    }

    public boolean isBajoStock() {
        return stock != null && stockMinimo != null && stock <= stockMinimo;
    }

    // --- Getters y Setters ---

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getUbicacionDeposito() {
        return ubicacionDeposito;
    }

    public void setUbicacionDeposito(String ubicacionDeposito) {
        this.ubicacionDeposito = ubicacionDeposito;
    }
}

