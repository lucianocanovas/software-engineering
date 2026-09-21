package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.TipoMedioPago;
import jakarta.persistence.*;

/**
 * =========================================================================================
 * CLASE ABSTRACTA / ENTIDAD: MedioPago
 * =========================================================================================
 * Demuestra la relación UML de HERENCIA y POLIMORFISMO en el módulo de pagos:
 * - Es la clase base abstracta de las modalidades admitidas por la tesorería del club:
 *   1. PagoEfectivo
 *   2. PagoTransferencia
 *   3. PagoMercadoPago
 * - Se asocia en COMPOSICIÓN estricta con Pago: Cada transacción posee exactamente un medio
 *   de pago con sus metadatos específicos.
 * - Mapeo ORM: InheritanceType.JOINED para normalizar las tablas especializadas en MySQL.
 */
@Entity
@Table(name = "medios_pago")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class MedioPago extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_medio_pago", nullable = false, length = 30)
    private TipoMedioPago tipo;

    @OneToOne(mappedBy = "medioPago")
    private Pago pago;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public MedioPago() {
    }

    public MedioPago(TipoMedioPago tipo) {
        this.tipo = tipo;
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / POLIMORFISMO
    // =====================================================================================

    /**
     * Retorna una descripción resumida y amigable para el comprobante de pago emitido.
     */
    public abstract String obtenerDetalleTransaccion();

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoMedioPago getTipo() {
        return tipo;
    }

    public void setTipo(TipoMedioPago tipo) {
        this.tipo = tipo;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
}
