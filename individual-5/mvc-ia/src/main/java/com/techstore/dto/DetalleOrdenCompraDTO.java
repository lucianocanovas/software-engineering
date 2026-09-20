package com.techstore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * =============================================================================
 * CAPA DTO: DetalleOrdenCompraDTO
 * =============================================================================
 * Transfiere los renglones individuales de una orden de compra, desacoplando
 * la relación de Composición de la entidad DetalleOrdenCompra.
 */
public class DetalleOrdenCompraDTO {

    private Long id;

    @NotNull(message = "Debe seleccionar un producto")
    private Long productoId;

    private String productoCodigo;
    private String productoNombre;
    private String productoTipo;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad mínima a ordenar es 1")
    private Integer cantidad = 1;

    @NotNull(message = "El precio unitario es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private Double precioUnitario = 0.0;

    private Double subtotal = 0.0;

    public DetalleOrdenCompraDTO() {
    }

    public DetalleOrdenCompraDTO(Long productoId, String productoCodigo, String productoNombre,
                                 String productoTipo, Integer cantidad, Double precioUnitario, Double subtotal) {
        this.productoId = productoId;
        this.productoCodigo = productoCodigo;
        this.productoNombre = productoNombre;
        this.productoTipo = productoTipo;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoCodigo() {
        return productoCodigo;
    }

    public void setProductoCodigo(String productoCodigo) {
        this.productoCodigo = productoCodigo;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public String getProductoTipo() {
        return productoTipo;
    }

    public void setProductoTipo(String productoTipo) {
        this.productoTipo = productoTipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }
}

