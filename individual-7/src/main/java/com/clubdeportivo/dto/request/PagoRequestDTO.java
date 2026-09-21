package com.clubdeportivo.dto.request;

import com.clubdeportivo.entity.enums.TipoMedioPago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * =========================================================================================
 * DTO REQUEST: PagoRequestDTO
 * =========================================================================================
 * Objeto de transferencia unificado para el registro transaccional de pagos de cuotas familiares:
 * - Soporta los tres medios de pago solicitados:
 *   1. EFECTIVO: Permite registrar número de caja, cajero y descuento comercial aplicado.
 *   2. TRANSFERENCIA: Registra CBU de origen y destino, banco y número de operación.
 *   3. MERCADO PAGO: Registra identificador del gateway (payment_id), preferencia o token QR.
 */
public class PagoRequestDTO {

    @NotNull(message = "Debe especificar la cuota a cancelar")
    private Long cuotaId;

    @NotNull(message = "El medio de pago es obligatorio (Efectivo, Transferencia o Mercado Pago)")
    private TipoMedioPago tipoMedioPago;

    @NotNull(message = "El monto abonado es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto a pagar debe ser mayor a cero")
    private BigDecimal montoAbonado;

    private String notas;

    // -------------------------------------------------------------------------------------
    // CAMPOS EXCLUSIVOS PARA EFECTIVO
    // -------------------------------------------------------------------------------------
    private BigDecimal descuentoAplicado = BigDecimal.ZERO;
    private String numeroCaja = "CAJA-01";
    private String cajeroResponsable = "Operador de Turno";

    // -------------------------------------------------------------------------------------
    // CAMPOS EXCLUSIVOS PARA TRANSFERENCIA BANCARIA
    // -------------------------------------------------------------------------------------
    private String cbuOrigen;
    private String cbuDestino = "0000003100010002000304"; // CBU institucional del Club
    private String bancoOrigen;
    private String numeroOperacion;
    private String comprobanteHash;

    // -------------------------------------------------------------------------------------
    // CAMPOS EXCLUSIVOS PARA MERCADO PAGO
    // -------------------------------------------------------------------------------------
    private String mpPaymentId;
    private String mpPreferenceId;
    private String qrCodeUrl;
    private String statusDetail = "accredited";

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getCuotaId() {
        return cuotaId;
    }

    public void setCuotaId(Long cuotaId) {
        this.cuotaId = cuotaId;
    }

    public TipoMedioPago getTipoMedioPago() {
        return tipoMedioPago;
    }

    public void setTipoMedioPago(TipoMedioPago tipoMedioPago) {
        this.tipoMedioPago = tipoMedioPago;
    }

    public BigDecimal getMontoAbonado() {
        return montoAbonado;
    }

    public void setMontoAbonado(BigDecimal montoAbonado) {
        this.montoAbonado = montoAbonado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public BigDecimal getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(BigDecimal descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public String getNumeroCaja() {
        return numeroCaja;
    }

    public void setNumeroCaja(String numeroCaja) {
        this.numeroCaja = numeroCaja;
    }

    public String getCajeroResponsable() {
        return cajeroResponsable;
    }

    public void setCajeroResponsable(String cajeroResponsable) {
        this.cajeroResponsable = cajeroResponsable;
    }

    public String getCbuOrigen() {
        return cbuOrigen;
    }

    public void setCbuOrigen(String cbuOrigen) {
        this.cbuOrigen = cbuOrigen;
    }

    public String getCbuDestino() {
        return cbuDestino;
    }

    public void setCbuDestino(String cbuDestino) {
        this.cbuDestino = cbuDestino;
    }

    public String getBancoOrigen() {
        return bancoOrigen;
    }

    public void setBancoOrigen(String bancoOrigen) {
        this.bancoOrigen = bancoOrigen;
    }

    public String getNumeroOperacion() {
        return numeroOperacion;
    }

    public void setNumeroOperacion(String numeroOperacion) {
        this.numeroOperacion = numeroOperacion;
    }

    public String getComprobanteHash() {
        return comprobanteHash;
    }

    public void setComprobanteHash(String comprobanteHash) {
        this.comprobanteHash = comprobanteHash;
    }

    public String getMpPaymentId() {
        return mpPaymentId;
    }

    public void setMpPaymentId(String mpPaymentId) {
        this.mpPaymentId = mpPaymentId;
    }

    public String getMpPreferenceId() {
        return mpPreferenceId;
    }

    public void setMpPreferenceId(String mpPreferenceId) {
        this.mpPreferenceId = mpPreferenceId;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getStatusDetail() {
        return statusDetail;
    }

    public void setStatusDetail(String statusDetail) {
        this.statusDetail = statusDetail;
    }
}
