package com.clubdeportivo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================================
 * ENTIDAD: GrupoFamiliar
 * =========================================================================================
 * Representa la unidad de suscripción familiar en el club deportivo:
 * - Demuestra la relación UML de AGREGACIÓN:
 *   - Posee un Socio Titular como cabeza de familia.
 *   - Integra de 0 a muchos FamiliarAdherente.
 *   - Si el grupo familiar se disuelve o se da de baja, los familiares adherentes y el socio
 *     titular continúan existiendo como registros en la entidad Persona (no mueren en cascada).
 * - Centraliza la facturación y el cobro de la Cuota Social Familiar mensual.
 *
 * Métodos de dominio:
 * - agregarIntegrante(FamiliarAdherente): Vincula un adherente al grupo manteniendo bidireccionalidad.
 * - cantidadIntegrantes(): Retorna la suma del titular más los familiares adherentes.
 */
@Entity
@Table(name = "grupos_familiares")
public class GrupoFamiliar extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 40)
    private String codigo;

    @Column(name = "fecha_constitucion", nullable = false)
    private LocalDate fechaConstitucion;

    @Column(name = "observaciones", length = 255)
    private String observaciones;

    /**
     * RELACIÓN: 1 Socio encabeza 0..1 GrupoFamiliar
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "socio_titular_id", referencedColumnName = "persona_id", nullable = false)
    private Socio titular;

    /**
     * RELACIÓN DE AGREGACIÓN: 1 GrupoFamiliar integra 0..* FamiliarAdherente
     */
    @OneToMany(mappedBy = "grupoFamiliar", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private List<FamiliarAdherente> integrantes = new ArrayList<>();

    /**
     * Historial de cuotas emitidas para este grupo familiar.
     */
    @OneToMany(mappedBy = "grupoFamiliar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cuota> cuotas = new ArrayList<>();

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public GrupoFamiliar() {
        this.fechaConstitucion = LocalDate.now();
    }

    public GrupoFamiliar(String codigo, Socio titular, String observaciones) {
        this();
        this.codigo = codigo;
        this.titular = titular;
        this.observaciones = observaciones;
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Agrega un nuevo familiar adherente al grupo de forma segura, manteniendo la consistencia
     * en ambos lados del enlace relacional.
     */
    public void agregarIntegrante(FamiliarAdherente adherente) {
        if (adherente != null) {
            this.integrantes.add(adherente);
            adherente.setGrupoFamiliar(this);
        }
    }

    /**
     * Remueve un familiar adherente del grupo.
     */
    public void removerIntegrante(FamiliarAdherente adherente) {
        if (adherente != null) {
            this.integrantes.remove(adherente);
            adherente.setGrupoFamiliar(null);
        }
    }

    /**
     * Calcula la cantidad total de miembros amparados bajo el plan familiar
     * (1 titular + cantidad de adherentes registrados).
     */
    public int cantidadIntegrantes() {
        int total = (this.titular != null) ? 1 : 0;
        if (this.integrantes != null) {
            total += this.integrantes.size();
        }
        return total;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaConstitucion() {
        return fechaConstitucion;
    }

    public void setFechaConstitucion(LocalDate fechaConstitucion) {
        this.fechaConstitucion = fechaConstitucion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Socio getTitular() {
        return titular;
    }

    public void setTitular(Socio titular) {
        this.titular = titular;
    }

    public List<FamiliarAdherente> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<FamiliarAdherente> integrantes) {
        this.integrantes = integrantes;
    }

    public List<Cuota> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }
}
