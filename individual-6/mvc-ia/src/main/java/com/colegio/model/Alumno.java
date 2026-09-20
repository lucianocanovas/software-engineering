package com.colegio.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================================
 * ENTIDAD: Alumno (Estudiante)
 * =========================================================================================
 * Especialización de la superclase Persona (Herencia UML).
 * Modela los datos de los estudiantes del colegio:
 * - codigo (matrícula institucional)
 * - fechaIngreso
 *
 * RELACIONES UML IMPLEMENTADAS:
 * 1. Herencia: Extiende Persona (hereda dni, nombre, apellido, email, sexo, fechaNacimiento, etc.).
 * 2. Agregación con Aula: El alumno está asignado a un aula pedagógica (grado + división).
 * 3. Asociación con Nota: El alumno es el titular de las calificaciones obtenidas en las evaluaciones.
 */
@Entity
@Table(name = "alumnos")
@PrimaryKeyJoinColumn(name = "id")
public class Alumno extends Persona {

    @Column(name = "codigo", unique = true, nullable = false)
    private Integer codigo;

    @Column(name = "fecha_ingreso", nullable = false)
    private LocalDate fechaIngreso;

    /**
     * AGREGACIÓN UML:
     * El Alumno forma parte de un Aula, pero tiene ciclo de vida autónomo.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aula_id")
    private Aula aula;

    /**
     * ASOCIACIÓN UML con Nota:
     * Un alumno tiene su historial académico compuesto por las notas de sus evaluaciones.
     */
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Nota> notas = new ArrayList<>();

    public Alumno() {
        super();
        setRol(Rol.ROLE_ALUMNO);
    }

    public Alumno(String dni, String nombre, String apellido, String email, Sexo sexo,
                  LocalDate fechaNacimiento, String password, Integer codigo,
                  LocalDate fechaIngreso, Aula aula) {
        super(dni, nombre, apellido, email, sexo, fechaNacimiento, password, Rol.ROLE_ALUMNO);
        this.codigo = codigo;
        this.fechaIngreso = fechaIngreso;
        this.aula = aula;
    }

    /**
     * Lógica de dominio: cálculo del promedio ponderado/general de notas del alumno.
     */
    public Double calcularPromedio() {
        if (notas == null || notas.isEmpty()) {
            return 0.0;
        }
        double suma = 0.0;
        for (Nota n : notas) {
            if (n.getPuntaje() != null) {
                suma += n.getPuntaje();
            }
        }
        return Math.round((suma / notas.size()) * 100.0) / 100.0;
    }

    // Getters y Setters
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }
}

