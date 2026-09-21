package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.EstadoPago;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * =========================================================================================
 * ENTIDAD: Pago
 * =========================================================================================
 * Representa el acto administrativo y financiero de cobranza de una cuota del club:
 * - Demuestra relación de COMPOSICIÓN con MedioPago: El Pago contiene de forma inseparable
 *   la entidad especializada de MedioPago (CascadeType.ALL, orphanRemoval = true).
 * - Demuestra relación de ASOCIACIÓN (1 a 1) con Cuota: Cancela la obligación monetaria.
 * - Método de dominio:
 *   - generarComprobante(): Construye un resumen textual legal del recibo emitido.
 */
@Entity
@Table(name = "pagos")
public class Pago extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_recibo", nullable = false, unique = true, length = 40)
    private String numeroRecibo;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDateTime fechaPago;

    @Column(name = "monto_abonado", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoAbonado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoPago estado = EstadoPago.COMPLETADO;

    @Column(name = "notas", length = 255)
    private String notas;

    /**
     * Cuota cancelada mediante este pago (Relación OneToOne).
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cuota_id", nullable = false, unique = true)
    private Cuota cuota;

    /**
     * RELACIÓN DE COMPOSICIÓN (1 posee 1 MedioPago):
     * Los detalles específicos de Efectivo, Transferencia o Mercado Pago son componentes
     * inseparables del registro de pago.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "medio_pago_id", referencedColumnName = "id", nullable = false)
    private MedioPago medioPago;

    /**
     * Socio que efectiviza el pago.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public Pago() {
        this.fechaPago = LocalDateTime.now();
        this.estado = EstadoPago.COMPLETADO;
    }

    public Pago(String numeroRecibo, BigDecimal montoAbonado, Cuota cuota, MedioPago medioPago, Socio socio) {
        this();
        this.numeroRecibo = numeroRecibo;
        this.montoAbonado = montoAbonado;
        this.cuota = cuota;
        this.medioPago = medioPago;
        this.socio = socio;
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Genera un comprobante formateado para constancia del socio y archivo de tesorería.
     */
    public String generarComprobante() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String fecha = (this.fechaPago != null) ? this.fechaPago.format(formatter) : "N/A";
        String detalleMedio = (this.medioPago != null) ? this.medioPago.obtenerDetalleTransaccion() : "N/A";
        String titular = (this.socio != null) ? this.socio.obtenerNombreCompleto() : "N/A";
        String periodo = (this.cuota != null) ? this.cuota.getPeriodo() : "N/A";

        return String.format(
            "==========================================================\n" +
            "            RECIBO OFICIAL DE COBRO - CLUB DEPORTIVO     \n" +
            "==========================================================\n" +
            "Recibo N°: %s\n" +
            "Fecha: %s\n" +
            "Socio Titular: %s\n" +
            "Periodo Abonado: %s\n" +
            "Monto Total: $%.2f\n" +
            "Medio de Pago: %s\n" +
            "Estado: %s\n" +
            "==========================================================",
            this.numeroRecibo, fecha, titular, periodo, this.montoAbonado, detalleMedio, this.estado
        );
    }

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

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Cuota getCuota() {
        return cuota;
    }

    public void setCuota(Cuota cuota) {
        this.cuota = cuota;
    }

    public MedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
        if (medioPago != null) {
            medioPago.setPago(this);
        }
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }
}
