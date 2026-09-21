package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.TipoMedioPago;
import jakarta.persistence.*;

/**
 * =========================================================================================
 * ENTIDAD: PagoMercadoPago
 * =========================================================================================
 * Especialización de MedioPago mediante HERENCIA (extends MedioPago):
 * - Modela la integración con la plataforma Fintech Mercado Pago (QR, Checkout Pro / API).
 * - Registra el identificador de pago oficial (payment_id), ID de preferencia para checkout,
 *   enlace/datos del código QR y detalle de estado devuelto por el gateway.
 */
@Entity
@Table(name = "pagos_mercadopago")
@PrimaryKeyJoinColumn(name = "medio_pago_id")
public class PagoMercadoPago extends MedioPago {

    @Column(name = "mp_payment_id", nullable = false, length = 60)
    private String mpPaymentId;

    @Column(name = "mp_preference_id", length = 80)
    private String mpPreferenceId;

    @Column(name = "qr_code_url", length = 255)
    private String qrCodeUrl;

    @Column(name = "status_detail", length = 80)
    private String statusDetail;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public PagoMercadoPago() {
        super(TipoMedioPago.MERCADO_PAGO);
    }

    public PagoMercadoPago(String mpPaymentId, String mpPreferenceId, String qrCodeUrl, String statusDetail) {
        super(TipoMedioPago.MERCADO_PAGO);
        this.mpPaymentId = mpPaymentId;
        this.mpPreferenceId = mpPreferenceId;
        this.qrCodeUrl = qrCodeUrl;
        this.statusDetail = (statusDetail != null) ? statusDetail : "accredited";
    }

    // =====================================================================================
    // POLIMORFISMO
    // =====================================================================================

    @Override
    public String obtenerDetalleTransaccion() {
        return String.format("Mercado Pago Digital - Payment ID #%s (Estado Gateway: %s)",
                this.mpPaymentId, (this.statusDetail != null ? this.statusDetail : "accredited"));
    }

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

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
