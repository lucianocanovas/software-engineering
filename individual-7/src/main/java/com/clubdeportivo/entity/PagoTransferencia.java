package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.TipoMedioPago;
import jakarta.persistence.*;

/**
 * =========================================================================================
 * ENTIDAD: PagoTransferencia
 * =========================================================================================
 * Especialización de MedioPago mediante HERENCIA (extends MedioPago):
 * - Modela transferencias electrónicas bancarias o desde billeteras virtuales (CBU/CVU/Alias).
 * - Registra cuentas de origen y destino, entidad bancaria, número de operación y hash de comprobante.
 */
@Entity
@Table(name = "pagos_transferencia")
@PrimaryKeyJoinColumn(name = "medio_pago_id")
public class PagoTransferencia extends MedioPago {

    @Column(name = "cbu_origen", length = 30)
    private String cbuOrigen;

    @Column(name = "cbu_destino", nullable = false, length = 30)
    private String cbuDestino;

    @Column(name = "banco_origen", length = 80)
    private String bancoOrigen;

    @Column(name = "numero_operacion", nullable = false, length = 60)
    private String numeroOperacion;

    @Column(name = "comprobante_hash", length = 100)
    private String comprobanteHash;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public PagoTransferencia() {
        super(TipoMedioPago.TRANSFERENCIA);
    }

    public PagoTransferencia(String cbuOrigen, String cbuDestino, String bancoOrigen,
                             String numeroOperacion, String comprobanteHash) {
        super(TipoMedioPago.TRANSFERENCIA);
        this.cbuOrigen = cbuOrigen;
        this.cbuDestino = cbuDestino;
        this.bancoOrigen = bancoOrigen;
        this.numeroOperacion = numeroOperacion;
        this.comprobanteHash = comprobanteHash;
    }

    // =====================================================================================
    // POLIMORFISMO
    // =====================================================================================

    @Override
    public String obtenerDetalleTransaccion() {
        return String.format("Transferencia Bancaria - Op #%s (Banco: %s, CBU Destino: %s)",
                this.numeroOperacion,
                (this.bancoOrigen != null ? this.bancoOrigen : "N/A"),
                this.cbuDestino);
    }

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

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
}
