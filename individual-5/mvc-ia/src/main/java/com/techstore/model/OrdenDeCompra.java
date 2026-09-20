package com.techstore.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 * CAPA MODELO / ORM: ENTIDAD OrdenDeCompra
 * =============================================================================
 * RELACIONES UML REPRESENTADAS:
 * 1. COMPOSICIÓN CON DetalleOrdenCompra (1 contiene 1..*):
 *    Se modela en JPA mediante CascadeType.ALL y orphanRemoval = true.
 *    La orden de compra es dueña del ciclo de vida de cada renglón de detalle.
 *
 * 2. ASOCIACIÓN CON Proveedor (0..* emitida a 1):
 *    La orden está asociada a un proveedor comercial mayorista.
 *
 * 3. ASOCIACIÓN CON EncargadoCompras / Usuario (1 registra 0..*):
 *    Registra qué usuario operativo generó la orden de abastecimiento.
 * -----------------------------------------------------------------------------
 * MÉTODOS DE DOMINIO DEL DIAGRAMA:
 * - agregarDetalle(): Vincula un renglón a la orden manteniendo coherencia bidireccional.
 * - calcularTotal(): Suma los subtotales de todos sus detalles.
 * - confirmarRecepcion(): Cambia el estado a RECIBIDA y actualiza el stock físico de productos.
 */
@Entity
@Table(name = "ordenes_compra")
public class OrdenDeCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", unique = true, nullable = false, length = 30)
    private String numero;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision = LocalDate.now();

    @Column(name = "fecha_entrega_estimada", nullable = false)
    private LocalDate fechaEntregaEstimada;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;

    @Column(name = "total", nullable = false)
    private Double total = 0.0;

    /**
     * RELACIÓN DE ASOCIACIÓN: Emitida a un Proveedor mayorista.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "proveedor_id", nullable = false)
    private Proveedor proveedor;

    /**
     * RELACIÓN DE ASOCIACIÓN: Registrada por un Encargado de Compras.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "encargado_compras_id", nullable = false)
    private Usuario encargadoCompras;

    /**
     * RELACIÓN DE COMPOSICIÓN (1 contiene 1..*):
     * CascadeType.ALL garantiza que al guardar, actualizar o borrar una orden,
     * todos sus detalles se procesen en cascada.
     * orphanRemoval = true elimina los detalles huérfanos que se remuevan de la lista.
     */
    @OneToMany(mappedBy = "ordenCompra", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DetalleOrdenCompra> detalles = new ArrayList<>();

    public OrdenDeCompra() {
    }

    public OrdenDeCompra(String numero, LocalDate fechaEntregaEstimada, Proveedor proveedor, Usuario encargadoCompras) {
        this.numero = numero;
        this.fechaEmision = LocalDate.now();
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.proveedor = proveedor;
        this.encargadoCompras = encargadoCompras;
        this.estado = EstadoOrden.PENDIENTE;
        this.total = 0.0;
    }

    /**
     * Método propio exigido en el diagrama: agregarDetalle()
     * Agrega un renglón de compra y asegura la referencia bidireccional.
     */
    public void agregarDetalle(DetalleOrdenCompra detalle) {
        if (detalle != null) {
            detalle.setOrdenCompra(this);
            this.detalles.add(detalle);
            calcularTotal();
        }
    }

    /**
     * Remueve un renglón de detalle de la orden.
     */
    public void removerDetalle(DetalleOrdenCompra detalle) {
        if (detalle != null) {
            this.detalles.remove(detalle);
            detalle.setOrdenCompra(null);
            calcularTotal();
        }
    }

    /**
     * Método propio exigido en el diagrama: calcularTotal()
     * Recorre la composición de detalles y suma sus subtotales.
     */
    public Double calcularTotal() {
        double acumulador = 0.0;
        if (detalles != null) {
            for (DetalleOrdenCompra d : detalles) {
                acumulador += d.calcularSubtotal();
            }
        }
        this.total = Math.round(acumulador * 100.0) / 100.0;
        return this.total;
    }

    /**
     * Método propio exigido en el diagrama: confirmarRecepcion()
     * CUMPLE CON EL REQUERIMIENTO CENTRAL:
     * "estos se actualizan en el stock de la empresa por medio de compras
     * a sus proveedores mayorista mediante el uso de órdenes de compra que registran el detalle de la misma."
     *
     * Si la orden está PENDIENTE, cambia a RECIBIDA e incrementa el stock de cada ProductoFisico.
     */
    public boolean confirmarRecepcion() {
        if (this.estado != EstadoOrden.PENDIENTE) {
            return false;
        }

        for (DetalleOrdenCompra detalle : this.detalles) {
            Producto producto = detalle.getProducto();
            if (producto instanceof ProductoFisico) {
                ProductoFisico fisico = (ProductoFisico) producto;
                fisico.incrementarStock(detalle.getCantidad());
            }
        }

        this.estado = EstadoOrden.RECIBIDA;
        return true;
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(LocalDate fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Usuario getEncargadoCompras() {
        return encargadoCompras;
    }

    public void setEncargadoCompras(Usuario encargadoCompras) {
        this.encargadoCompras = encargadoCompras;
    }

    public List<DetalleOrdenCompra> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompra> detalles) {
        this.detalles = detalles;
        calcularTotal();
    }
}

