package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.EstadoCuota;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * =========================================================================================
 * ENTIDAD: Cuota
 * =========================================================================================
 * Modela el canon o arancel mensual que debe abonar cada familia / socio titular:
 * - Demuestra relación de ASOCIACIÓN con GrupoFamiliar y Socio: El grupo familiar o socio titular
 *   abona cuotas periódicas.
 * - Demuestra relación de ASOCIACIÓN (1 a 0..1) con Pago: Una cuota saldada posee un Pago asociado.
 * - Métodos de dominio:
 *   - estaVencida(): Compara la fecha de vencimiento contra el día de la fecha y su estado.
 *   - calcularTotal(): Calcula el importe final sumando recargo por mora si está vencida.
 */
@Entity
@Table(name = "cuotas")
public class Cuota extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "periodo", nullable = false, length = 30)
    private String periodo;

    @Column(name = "monto_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal montoBase;

    @Column(name = "monto_recargo", precision = 10, scale = 2)
    private BigDecimal montoRecargo = BigDecimal.ZERO;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDate fechaVencimiento;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoCuota estado = EstadoCuota.PENDIENTE;

    /**
     * Socio Titular responsable de la cuenta familiar.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_titular_id", nullable = false)
    private Socio socioTitular;

    /**
     * Grupo Familiar amparado por esta cuota social.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_familiar_id")
    private GrupoFamiliar grupoFamiliar;

    /**
     * Pago que canceló esta cuota (si ya fue abonada).
     */
    @OneToOne(mappedBy = "cuota", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Pago pago;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public Cuota() {
        this.estado = EstadoCuota.PENDIENTE;
        this.montoRecargo = BigDecimal.ZERO;
    }

    public Cuota(String periodo, BigDecimal montoBase, BigDecimal montoRecargo,
                 LocalDate fechaVencimiento, Socio socioTitular, GrupoFamiliar grupoFamiliar) {
        this();
        this.periodo = periodo;
        this.montoBase = montoBase;
        this.montoRecargo = (montoRecargo != null) ? montoRecargo : BigDecimal.ZERO;
        this.fechaVencimiento = fechaVencimiento;
        this.socioTitular = socioTitular;
        this.grupoFamiliar = grupoFamiliar;
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Determina si la cuota ha superado el plazo de vencimiento pactado sin haber sido pagada.
     */
    public boolean estaVencida() {
        if (this.estado == EstadoCuota.PAGADA) {
            return false;
        }
        return this.fechaVencimiento != null && LocalDate.now().isAfter(this.fechaVencimiento);
    }

    /**
     * Calcula el monto exigible a la fecha actual:
     * Si está vencida, aplica automáticamente el recargo por mora.
     */
    public BigDecimal calcularTotal() {
        BigDecimal base = (this.montoBase != null) ? this.montoBase : BigDecimal.ZERO;
        if (estaVencida()) {
            BigDecimal recargo = (this.montoRecargo != null) ? this.montoRecargo : BigDecimal.ZERO;
            return base.add(recargo);
        }
        return base;
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

    public Socio getSocioTitular() {
        return socioTitular;
    }

    public void setSocioTitular(Socio socioTitular) {
        this.socioTitular = socioTitular;
    }

    public GrupoFamiliar getGrupoFamiliar() {
        return grupoFamiliar;
    }

    public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
        this.grupoFamiliar = grupoFamiliar;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
        if (pago != null && pago.getCuota() != this) {
            pago.setCuota(this);
        }
    }
}
