package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.Parentesco;
import jakarta.persistence.*;

/**
 * =========================================================================================
 * ENTIDAD: FamiliarAdherente
 * =========================================================================================
 * Especialización de Persona mediante HERENCIA (extends Persona):
 * - Representa a los integrantes del núcleo familiar ligados al Socio Titular.
 * - Forma parte de una AGREGACIÓN con GrupoFamiliar (el adherente existe como persona
 *   independientemente de si forma parte del grupo familiar o si este se disuelve).
 * - Método de negocio:
 *   - esMenorDeEdad(): Evalúa si la persona tiene menos de 18 años para aplicar normativas
 *     de seguro, autorizaciones parentales y aranceles diferenciales.
 */
@Entity
@Table(name = "familiares_adherentes")
@PrimaryKeyJoinColumn(name = "persona_id")
public class FamiliarAdherente extends Persona {

    @Enumerated(EnumType.STRING)
    @Column(name = "parentesco", nullable = false, length = 30)
    private Parentesco parentesco;

    /**
     * RELACIÓN DE AGREGACIÓN:
     * Varios familiares adherentes integran un GrupoFamiliar.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grupo_familiar_id")
    private GrupoFamiliar grupoFamiliar;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public FamiliarAdherente() {
        super();
    }

    public FamiliarAdherente(String dni, String nombre, String apellido, java.time.LocalDate fechaNacimiento,
                             String telefono, String email, Parentesco parentesco) {
        super(dni, nombre, apellido, fechaNacimiento, telefono, email);
        this.parentesco = parentesco;
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Determina si el adherente es menor de edad legal (menor a 18 años).
     * Relevante para requisitos de tutor en el ingreso al club o natatorio.
     */
    public boolean esMenorDeEdad() {
        return this.calcularEdad() < 18;
    }

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Parentesco getParentesco() {
        return parentesco;
    }

    public void setParentesco(Parentesco parentesco) {
        this.parentesco = parentesco;
    }

    public GrupoFamiliar getGrupoFamiliar() {
        return grupoFamiliar;
    }

    public void setGrupoFamiliar(GrupoFamiliar grupoFamiliar) {
        this.grupoFamiliar = grupoFamiliar;
    }
}
