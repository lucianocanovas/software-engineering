package com.clubdeportivo.dto.response;

import com.clubdeportivo.entity.enums.EstadoCuota;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * =========================================================================================
 * DTO RESPONSE: CuotaResponseDTO
 * =========================================================================================
 * Representa el estado financiero de una cuota de socio o grupo familiar.
 */
public class CuotaResponseDTO {

    private Long id;
    private String periodo;
    private BigDecimal montoBase;
    private BigDecimal montoRecargo;
    private BigDecimal totalExigible;
    private LocalDate fechaVencimiento;
    private LocalDate fechaPago;
    private EstadoCuota estado;
    private boolean vencida;
    private Long socioTitularId;
    private String socioTitularNombre;
    private String socioTitularNumero;
    private Long grupoFamiliarId;
    private String grupoFamiliarCodigo;
    private String numeroRecibo;
    private Long pagoId;

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getMontoBase() {
        return montoBase;
    }

    public void setMontoBase(BigDecimal montoBase) {
        this.montoBase = montoBase;
    }

    public BigDecimal getMontoRecargo() {
        return montoRecargo;
    }

    public void setMontoRecargo(BigDecimal montoRecargo) {
        this.montoRecargo = montoRecargo;
    }

    public BigDecimal getTotalExigible() {
        return totalExigible;
    }

    public void setTotalExigible(BigDecimal totalExigible) {
        this.totalExigible = totalExigible;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public EstadoCuota getEstado() {
        return estado;
    }

    public void setEstado(EstadoCuota estado) {
        this.estado = estado;
    }

    public boolean isVencida() {
        return vencida;
    }

    public void setVencida(boolean vencida) {
        this.vencida = vencida;
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

    public String getSocioTitularNumero() {
        return socioTitularNumero;
    }

    public void setSocioTitularNumero(String socioTitularNumero) {
        this.socioTitularNumero = socioTitularNumero;
    }

    public Long getGrupoFamiliarId() {
        return grupoFamiliarId;
    }

    public void setGrupoFamiliarId(Long grupoFamiliarId) {
        this.grupoFamiliarId = grupoFamiliarId;
    }

    public String getGrupoFamiliarCodigo() {
        return grupoFamiliarCodigo;
    }

    public void setGrupoFamiliarCodigo(String grupoFamiliarCodigo) {
        this.grupoFamiliarCodigo = grupoFamiliarCodigo;
    }

    public String getNumeroRecibo() {
        return numeroRecibo;
    }

    public void setNumeroRecibo(String numeroRecibo) {
        this.numeroRecibo = numeroRecibo;
    }

    public Long getPagoId() {
        return pagoId;
    }

    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }
}
