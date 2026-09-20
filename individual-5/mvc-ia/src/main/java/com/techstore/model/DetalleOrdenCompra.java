package com.techstore.model;

import jakarta.persistence.*;

/**
 * =============================================================================
 * CAPA MODELO / ORM: ENTIDAD DetalleOrdenCompra
 * =============================================================================
 * RELACIONES UML REPRESENTADAS:
 * 1. COMPOSICIÓN (1 contiene 1..*):
 *    Un DetalleOrdenCompra NO tiene existencia propia fuera de una OrdenDeCompra.
 *    Si la orden se elimina, sus detalles se eliminan en cascada. El ciclo de vida
 *    del detalle está estrictamente subordinado al de la orden.
 *
 * 2. ASOCIACIÓN (0..* referencia 1 Producto):
 *    Cada renglón de compra hace referencia al Producto tecnológico adquirido
 *    y su precio mayorista pactado.
 */
@Entity
@Table(name = "detalles_orden_compra")
public class DetalleOrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * RELACIÓN DE COMPOSICIÓN:
     * La clave foránea pertenece a la cabecera OrdenDeCompra.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "orden_compra_id", nullable = false)
    private OrdenDeCompra ordenCompra;

    /**
     * RELACIÓN DE ASOCIACIÓN:
     * Referencia al producto comprado (físico o digital).
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad = 1;

    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario = 0.0;

    @Column(name = "subtotal", nullable = false)
    private Double subtotal = 0.0;

    public DetalleOrdenCompra() {
    }

    public DetalleOrdenCompra(Producto producto, Integer cantidad, Double precioUnitario) {
        this.producto = producto;
        this.cantidad = cantidad != null ? cantidad : 1;
        this.precioUnitario = precioUnitario != null ? precioUnitario : 0.0;
        calcularSubtotal();
    }

    /**
     * Método propio exigido en el diagrama: calcularSubtotal()
     * Multiplica la cantidad adquirida por el precio unitario pactado con el mayorista.
     */
    public Double calcularSubtotal() {
        if (cantidad != null && precioUnitario != null) {
            this.subtotal = Math.round(cantidad * precioUnitario * 100.0) / 100.0;
        } else {
            this.subtotal = 0.0;
        }
        return this.subtotal;
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrdenDeCompra getOrdenCompra() {
        return ordenCompra;
    }

    public void setOrdenCompra(OrdenDeCompra ordenCompra) {
        this.ordenCompra = ordenCompra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}

