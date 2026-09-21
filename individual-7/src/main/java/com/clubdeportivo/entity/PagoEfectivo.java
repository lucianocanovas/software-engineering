package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.TipoMedioPago;
import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * =========================================================================================
 * ENTIDAD: PagoEfectivo
 * =========================================================================================
 * Especialización de MedioPago mediante HERENCIA (extends MedioPago):
 * - Modela el cobro en moneda de curso legal presencial en la caja o administración del club.
 * - Registra eventuales descuentos por pago contado/anticipado, caja receptora y responsable.
 */
@Entity
@Table(name = "pagos_efectivo")
@PrimaryKeyJoinColumn(name = "medio_pago_id")
public class PagoEfectivo extends MedioPago {

    @Column(name = "descuento_aplicado", precision = 10, scale = 2)
    private BigDecimal descuentoAplicado = BigDecimal.ZERO;

    @Column(name = "numero_caja", nullable = false, length = 20)
    private String numeroCaja;

    @Column(name = "cajero_responsable", nullable = false, length = 80)
    private String cajeroResponsable;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public PagoEfectivo() {
        super(TipoMedioPago.EFECTIVO);
        this.descuentoAplicado = BigDecimal.ZERO;
    }

    public PagoEfectivo(BigDecimal descuentoAplicado, String numeroCaja, String cajeroResponsable) {
        super(TipoMedioPago.EFECTIVO);
        this.descuentoAplicado = (descuentoAplicado != null) ? descuentoAplicado : BigDecimal.ZERO;
        this.numeroCaja = numeroCaja;
        this.cajeroResponsable = cajeroResponsable;
    }

    // =====================================================================================
    // POLIMORFISMO
    // =====================================================================================

    @Override
    public String obtenerDetalleTransaccion() {
        return String.format("Efectivo en Caja %s (Receptor: %s) - Descuento: $%.2f",
                this.numeroCaja, this.cajeroResponsable, this.descuentoAplicado);
    }

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

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
}
