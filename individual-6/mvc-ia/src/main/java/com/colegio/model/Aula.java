package com.colegio.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * =========================================================================================
 * ENTIDAD: Aula
 * =========================================================================================
 * Representa la sección, comisión o división física/pedagógica de los alumnos (ej. "1° A - Turno Mañana").
 *
 * RELACIONES UML IMPLEMENTADAS:
 * 1. Composición con Grado: El aula forma parte integral e inseparable del Grado escolar.
 * 2. Agregación con Alumno (1 Aula contiene 0..* Alumnos):
 *    Relación "todo-parte" débil. El aula agrupa temporalmente a los alumnos matriculados.
 *    Si el aula se disuelve, se fusiona o se borra del sistema, los Alumnos NO se destruyen;
 *    conservan su existencia independiente en el colegio (no lleva CascadeType.REMOVE ni orphanRemoval).
 * 3. Asociación con Profesor: Los docentes tienen asignadas una o varias aulas donde imparten clases.
 */
@Entity
@Table(name = "aulas")
public class Aula extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "division", nullable = false, length = 10)
    private String division; // Ej: "A", "B", "C"

    @Column(name = "turno", nullable = false, length = 30)
    private String turno; // Ej: "Mañana", "Tarde", "Noche"

    @Column(name = "capacidad", nullable = false)
    private Integer capacidad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;

    /**
     * AGREGACIÓN UML:
     * Un Aula agrupa alumnos. Sin embargo, no hay dependencia existencial (ciclo de vida independiente).
     * Por ello no se usa orphanRemoval ni CascadeType.ALL, cumpliendo con la definición formal de Agregación.
     */
    @OneToMany(mappedBy = "aula", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Alumno> alumnos = new ArrayList<>();

    @ManyToMany(mappedBy = "aulas")
    private List<Profesor> profesores = new ArrayList<>();

    public Aula() {
    }

    public Aula(String division, String turno, Integer capacidad, Grado grado) {
        this.division = division;
        this.turno = turno;
        this.capacidad = capacidad;
        this.grado = grado;
    }

    public String getNombreCompleto() {
        String base = (grado != null ? grado.getNombreCompleto() : "Grado") + " \"" + division + "\"";
        return base + " (" + turno + ")";
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public List<Profesor> getProfesores() {
        return profesores;
    }

    public void setProfesores(List<Profesor> profesores) {
        this.profesores = profesores;
    }
}

