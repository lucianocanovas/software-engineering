package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.CategoriaSocio;
import com.clubdeportivo.entity.enums.EstadoCuota;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================================
 * ENTIDAD: Socio
 * =========================================================================================
 * Especialización de Persona mediante HERENCIA (extends Persona):
 * - Representa al socio registrado formalmente en el padrón del club deportivo.
 * - Encabeza (como titular) un GrupoFamiliar (relación 1 a 0..1).
 * - Métodos de negocio:
 *   - estaAlDia(): Verifica si el socio no registra cuotas impagas o vencidas.
 *   - darDeBaja(): Desactiva al socio y cancela permisos de acceso.
 */
@Entity
@Table(name = "socios")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Socio extends Persona {

    @Column(name = "numero_socio", nullable = false, unique = true, length = 30)
    private String numeroSocio;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false, length = 30)
    private CategoriaSocio categoria;

    /**
     * RELACIÓN CON GRUPO FAMILIAR (1 encabeza 0..1):
     * El socio titular encabeza su grupo familiar, centralizando las cuotas del hogar.
     */
    @OneToOne(mappedBy = "titular", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private GrupoFamiliar grupoFamiliar;

    /**
     * Historial de cuotas asignadas al socio / grupo familiar.
     */
    @OneToMany(mappedBy = "socioTitular", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cuota> cuotas = new ArrayList<>();

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public Socio() {
        super();
        this.fechaAlta = LocalDate.now();
        this.categoria = CategoriaSocio.ACTIVO;
    }

    public Socio(String dni, String nombre, String apellido, LocalDate fechaNacimiento,
                 String telefono, String email, String numeroSocio, CategoriaSocio categoria) {
        super(dni, nombre, apellido, fechaNacimiento, telefono, email);
        this.numeroSocio = numeroSocio;
        this.categoria = categoria != null ? categoria : CategoriaSocio.ACTIVO;
        this.fechaAlta = LocalDate.now();
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Determina si el socio se encuentra habilitado financieramente para ingresar a las
     * instalaciones deportivas y participar de actividades.
     * Regla: No debe poseer cuotas en estado VENCIDA ni pendientes fuera de término.
     */
    public boolean estaAlDia() {
        if (!Boolean.TRUE.equals(this.getActivo())) {
            return false;
        }
        if (this.cuotas == null || this.cuotas.isEmpty()) {
            return true;
        }
        return this.cuotas.stream().noneMatch(c ->
                c.getEstado() == EstadoCuota.VENCIDA ||
                (c.getEstado() == EstadoCuota.PENDIENTE && c.estaVencida())
        );
    }

    /**
     * Da de baja al socio en el sistema, inactivando su acceso físico al club.
     */
    public void darDeBaja() {
        this.setActivo(false);
    }

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public String getNumeroSocio() {
        return numeroSocio;
    }

    public void setNumeroSocio(String numeroSocio) {
        this.numeroSocio = numeroSocio;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public CategoriaSocio getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaSocio categoria) {
        this.categoria = categoria;
    }

    public GrupoFamiliar getGrupoFamiliar() {
        return grupoFamiliar;
    }

    public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
        this.grupoFamiliar = grupoFamiliar;
        if (grupoFamiliar != null && grupoFamiliar.getTitular() != this) {
            grupoFamiliar.setTitular(this);
        }
    }

    public List<Cuota> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }
}
