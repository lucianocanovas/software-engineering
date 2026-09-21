package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.TipoMovimiento;
import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 * =========================================================================================
 * ENTIDAD: RegistroAcceso
 * =========================================================================================
 * Representa cada evento de paso físico (Entrada o Salida) en las instalaciones del club:
 * - Demuestra relación de ASOCIACIÓN con Persona (1 Persona genera 0..* RegistroAcceso).
 * - Demuestra relación de ASOCIACIÓN con PuntoDeAcceso (0..* se produce en 1 PuntoDeAcceso).
 * - Métodos de dominio:
 *   - registrarEntrada(): Fija la marca temporal de ingreso y tipo de movimiento.
 *   - registrarSalida(): Fija la marca temporal de egreso.
 *   - calcularPermanenciaMinutos(): Calcula el intervalo transcurrido dentro del club en minutos.
 */
@Entity
@Table(name = "registros_acceso")
public class RegistroAcceso extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora_entrada")
    private LocalDateTime fechaHoraEntrada;

    @Column(name = "fecha_hora_salida")
    private LocalDateTime fechaHoraSalida;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_movimiento", nullable = false, length = 20)
    private TipoMovimiento tipoMovimiento;

    @Column(name = "observacion", length = 255)
    private String observacion;

    /**
     * Persona (Socio, Adherente o Empleado) que realizó el acceso.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "persona_id", nullable = false)
    private Persona persona;

    /**
     * Molinete o terminal física donde se produjo el evento.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "punto_de_acceso_id", nullable = false)
    private PuntoDeAcceso puntoDeAcceso;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public RegistroAcceso() {
    }

    public RegistroAcceso(Persona persona, PuntoDeAcceso puntoDeAcceso, TipoMovimiento tipoMovimiento) {
        this.persona = persona;
        this.puntoDeAcceso = puntoDeAcceso;
        this.tipoMovimiento = tipoMovimiento;
        if (tipoMovimiento == TipoMovimiento.ENTRADA) {
            this.fechaHoraEntrada = LocalDateTime.now();
        } else {
            this.fechaHoraSalida = LocalDateTime.now();
        }
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Registra un ingreso oficial al predio con timestamp actual.
     */
    public void registrarEntrada() {
        this.tipoMovimiento = TipoMovimiento.ENTRADA;
        this.fechaHoraEntrada = LocalDateTime.now();
    }

    /**
     * Registra el egreso cerrando la estancia en el club.
     */
    public void registrarSalida() {
        this.tipoMovimiento = TipoMovimiento.SALIDA;
        this.fechaHoraSalida = LocalDateTime.now();
    }

    /**
     * Calcula la cantidad de minutos que la persona permaneció en el club.
     * @return Minutos transcurridos entre entrada y salida, o hasta el momento actual si aún no egresó.
     */
    public long calcularPermanenciaMinutos() {
        if (this.fechaHoraEntrada == null) {
            return 0;
        }
        LocalDateTime fin = (this.fechaHoraSalida != null) ? this.fechaHoraSalida : LocalDateTime.now();
        return Duration.between(this.fechaHoraEntrada, fin).toMinutes();
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

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public PuntoDeAcceso getPuntoDeAcceso() {
        return puntoDeAcceso;
    }

    public void setPuntoDeAcceso(PuntoDeAcceso puntoDeAcceso) {
        this.puntoDeAcceso = puntoDeAcceso;
    }
}
