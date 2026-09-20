package com.colegio.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================================
 * ENTIDAD: Profesor (Docente)
 * =========================================================================================
 * Especialización de la superclase Persona (Herencia UML).
 * Incorpora los atributos requeridos por la especificación:
 * - legajo
 * - especialidad
 * - salario
 *
 * RELACIONES UML IMPLEMENTADAS:
 * 1. Herencia: Extiende Persona (hereda dni, nombre, apellido, email, sexo, fechaNacimiento, password, rol).
 * 2. Asociación con Materia: Dicta una o más asignaturas curriculares.
 * 3. Asociación con Aula: Imparte clases en una o varias aulas asignadas.
 * 4. Asociación con Evaluacion: Administra y califica evaluaciones para los alumnos.
 */
@Entity
@Table(name = "profesores")
@PrimaryKeyJoinColumn(name = "id")
public class Profesor extends Persona {

    @Column(name = "legajo", unique = true, nullable = false)
    private Integer legajo;

    @Column(name = "especialidad", nullable = false, length = 100)
    private String especialidad;

    @Column(name = "salario")
    private Double salario;

    /**
     * ASOCIACIÓN UML con Materia:
     * Un docente puede dictar varias asignaturas y una asignatura puede contar con varios docentes.
     */
    @ManyToMany
    @JoinTable(
        name = "profesor_materia",
        joinColumns = @JoinColumn(name = "profesor_id"),
        inverseJoinColumns = @JoinColumn(name = "materia_id")
    )
    private List<Materia> materias = new ArrayList<>();

    /**
     * ASOCIACIÓN UML con Aula:
     * El docente acude a impartir clase en distintas aulas del colegio.
     */
    @ManyToMany
    @JoinTable(
        name = "profesor_aula",
        joinColumns = @JoinColumn(name = "profesor_id"),
        inverseJoinColumns = @JoinColumn(name = "aula_id")
    )
    private List<Aula> aulas = new ArrayList<>();

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private List<Evaluacion> evaluaciones = new ArrayList<>();

    public Profesor() {
        super();
        setRol(Rol.ROLE_DOCENTE);
    }

    public Profesor(String dni, String nombre, String apellido, String email, Sexo sexo,
                    LocalDate fechaNacimiento, String password, Integer legajo,
                    String especialidad, Double salario) {
        super(dni, nombre, apellido, email, sexo, fechaNacimiento, password, Rol.ROLE_DOCENTE);
        this.legajo = legajo;
        this.especialidad = especialidad;
        this.salario = salario;
    }

    public void agregarMateria(Materia materia) {
        if (!materias.contains(materia)) {
            materias.add(materia);
            materia.getProfesores().add(this);
        }
    }

    public void agregarAula(Aula aula) {
        if (!aulas.contains(aula)) {
            aulas.add(aula);
            aula.getProfesores().add(this);
        }
    }

    // Getters y Setters
    public Integer getLegajo() {
        return legajo;
    }

    public void setLegajo(Integer legajo) {
        this.legajo = legajo;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Double getSalario() {
        return salario;
    }

    public void setSalario(Double salario) {
        this.salario = salario;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public List<Evaluacion> getEvaluaciones() {
        return evaluaciones;
    }

    public void setEvaluaciones(List<Evaluacion> evaluaciones) {
        this.evaluaciones = evaluaciones;
    }
}

