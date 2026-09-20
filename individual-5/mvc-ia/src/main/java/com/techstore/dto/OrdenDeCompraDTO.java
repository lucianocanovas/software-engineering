package com.techstore.dto;

import com.techstore.model.EstadoOrden;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * =============================================================================
 * CAPA DTO: OrdenDeCompraDTO
 * =============================================================================
 * Objeto de transferencia completo para la gestión de compras a mayoristas.
 * Transporta la cabecera y la colección de renglones detallados (Composición).
 */
public class OrdenDeCompraDTO {

    private Long id;

    @NotBlank(message = "El número de orden es obligatorio")
    private String numero;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaEmision = LocalDate.now();

    @NotNull(message = "La fecha estimada de entrega es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaEntregaEstimada = LocalDate.now().plusDays(7);

    private EstadoOrden estado = EstadoOrden.PENDIENTE;

    private Double total = 0.0;

    @NotNull(message = "Debe seleccionar un proveedor mayorista")
    private Long proveedorId;
    private String proveedorRazonSocial;

    private Long encargadoComprasId;
    private String encargadoComprasNombre;

    private List<DetalleOrdenCompraDTO> detalles = new ArrayList<>();

    public OrdenDeCompraDTO() {
    }

    public boolean isPendiente() {
        return estado == EstadoOrden.PENDIENTE;
    }

    public boolean isRecibida() {
        return estado == EstadoOrden.RECIBIDA;
    }

    public boolean isCancelada() {
        return estado == EstadoOrden.CANCELADA;
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

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getProveedorRazonSocial() {
        return proveedorRazonSocial;
    }

    public void setProveedorRazonSocial(String proveedorRazonSocial) {
        this.proveedorRazonSocial = proveedorRazonSocial;
    }

    public Long getEncargadoComprasId() {
        return encargadoComprasId;
    }

    public void setEncargadoComprasId(Long encargadoComprasId) {
        this.encargadoComprasId = encargadoComprasId;
    }

    public String getEncargadoComprasNombre() {
        return encargadoComprasNombre;
    }

    public void setEncargadoComprasNombre(String encargadoComprasNombre) {
        this.encargadoComprasNombre = encargadoComprasNombre;
    }

    public List<DetalleOrdenCompraDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenCompraDTO> detalles) {
        this.detalles = detalles;
    }
}

