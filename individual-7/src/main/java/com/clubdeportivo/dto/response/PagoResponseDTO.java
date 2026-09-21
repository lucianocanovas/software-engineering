package com.clubdeportivo.dto.response;

import com.clubdeportivo.entity.enums.EstadoPago;
import com.clubdeportivo.entity.enums.TipoMedioPago;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * =========================================================================================
 * DTO RESPONSE: PagoResponseDTO
 * =========================================================================================
 * Presenta el recibo final de cobro, detallando la modalidad de pago utilizada (Efectivo,
 * Transferencia o Mercado Pago) y el comprobante legal generado.
 */
public class PagoResponseDTO {

    private Long id;
    private String numeroRecibo;
    private LocalDateTime fechaPago;
    private BigDecimal montoAbonado;
    private EstadoPago estado;
    private TipoMedioPago tipoMedioPago;
    private String detalleMedioPago;
    private Long cuotaId;
    private String cuotaPeriodo;
    private Long socioTitularId;
    private String socioTitularNombre;
    private String socioNumero;
    private String comprobanteTexto;
    private String notas;

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public BigDecimal getMontoAbonado() {
        return montoAbonado;
    }

    public void setMontoAbonado(BigDecimal montoAbonado) {
        this.montoAbonado = montoAbonado;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public TipoMedioPago getTipoMedioPago() {
        return tipoMedioPago;
    }

    public void setTipoMedioPago(TipoMedioPago tipoMedioPago) {
        this.tipoMedioPago = tipoMedioPago;
    }

    public String getDetalleMedioPago() {
        return detalleMedioPago;
    }

    public void setDetalleMedioPago(String detalleMedioPago) {
        this.detalleMedioPago = detalleMedioPago;
    }

    public Long getCuotaId() {
        return cuotaId;
    }

    public void setCuotaId(Long cuotaId) {
        this.cuotaId = cuotaId;
    }

    public String getCuotaPeriodo() {
        return cuotaPeriodo;
    }

    public void setCuotaPeriodo(String cuotaPeriodo) {
        this.cuotaPeriodo = cuotaPeriodo;
    }

    public Long getSocioTitularId() {
        return socioTitularId;
    }

    public void setSocioTitularId(Long socioTitularId) {
        this.socioTitularId = socioTitularId;
    }

    public String getSocioTitularNombre() {
        return socioTitularNombre;
    }

    public void setSocioTitularNombre(String socioTitularNombre) {
        this.socioTitularNombre = socioTitularNombre;
    }

    public String getSocioNumero() {
        return socioNumero;
    }

    public void setSocioNumero(String socioNumero) {
        this.socioNumero = socioNumero;
    }

    public String getComprobanteTexto() {
        return comprobanteTexto;
    }

    public void setComprobanteTexto(String comprobanteTexto) {
        this.comprobanteTexto = comprobanteTexto;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
