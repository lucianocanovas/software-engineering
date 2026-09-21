package com.clubdeportivo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================================
 * ENTIDAD: Profesor
 * =========================================================================================
 * Especialización de Empleado mediante HERENCIA multinivel (Profesor -> Empleado -> Persona):
 * - Representa a los instructores y directores técnicos a cargo de las disciplinas del club.
 * - Demuestra relación de AGREGACIÓN con Actividad: Dicta una o más actividades.
 * - Método de dominio:
 *   - asignarActividad(Actividad): Asigna la responsabilidad docente de una disciplina deportiva.
 */
@Entity
@Table(name = "profesores")
@PrimaryKeyJoinColumn(name = "persona_id")
public class Profesor extends Empleado {

    @Column(name = "especialidad", nullable = false, length = 80)
    private String especialidad;

    @Column(name = "matricula", nullable = false, unique = true, length = 40)
    private String matricula;

    /**
     * Actividades deportivas dictadas por este profesor (Relación ManyToMany).
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "profesor_actividades",
        joinColumns = @JoinColumn(name = "profesor_id"),
        inverseJoinColumns = @JoinColumn(name = "actividad_id")
    )
    private List<Actividad> actividades = new ArrayList<>();

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public Profesor() {
        super();
    }

    public Profesor(String dni, String nombre, String apellido, LocalDate fechaNacimiento,
                    String telefono, String email, String legajo, String cargo,
                    String especialidad, String matricula) {
        super(dni, nombre, apellido, fechaNacimiento, telefono, email, legajo, cargo);
        this.especialidad = especialidad;
        this.matricula = matricula;
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Vincula una actividad deportiva al profesor asegurando consistencia bidireccional.
     */
    public void asignarActividad(Actividad actividad) {
        if (actividad != null && !this.actividades.contains(actividad)) {
            this.actividades.add(actividad);
            if (!actividad.getProfesores().contains(this)) {
                actividad.getProfesores().add(this);
            }
        }
    }

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }
}
