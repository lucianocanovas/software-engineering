package com.colegio.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * =========================================================================================
 * ENTIDAD: Grado
 * =========================================================================================
 * Representa el nivel/año académico dentro de la institución (ej. "1° Año Secundaria").
 *
 * RELACIONES UML IMPLEMENTADAS:
 * 1. Composición inversa con Colegio: Pertenece necesariamente a un colegio.
 * 2. Composición con Aula (1 Grado contiene 1..* Aulas):
 *    Las aulas (divisiones como 'A', 'B') dependen existencialmente del grado.
 * 3. Asociación con Materia: Cada grado tiene una currícula o conjunto de materias asignadas.
 */
@Entity
@Table(name = "grados")
public class Grado extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nivel", nullable = false, length = 50)
    private String nivel; // Ej: "Primario", "Secundario"

    @Column(name = "anio", nullable = false)
    private Integer anio; // Ej: 1, 2, 3...

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colegio_id", nullable = false)
    private Colegio colegio;

    /**
     * COMPOSICIÓN UML:
     * Un Grado contiene varias Aulas (divisiones). Si se elimina el Grado,
     * las Aulas asociadas dejan de tener sentido y se eliminan en cascada.
     */
    @OneToMany(mappedBy = "grado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Aula> aulas = new ArrayList<>();

    @OneToMany(mappedBy = "grado", cascade = CascadeType.ALL)
    private List<Materia> materias = new ArrayList<>();

    public Grado() {
    }

    public Grado(String nivel, Integer anio, Colegio colegio) {
        this.nivel = nivel;
        this.anio = anio;
        this.colegio = colegio;
    }

    public String getNombreCompleto() {
        return anio + "° " + nivel;
    }

    public void agregarAula(Aula aula) {
        aulas.add(aula);
        aula.setGrado(this);
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Colegio getColegio() {
        return colegio;
    }

    public void setColegio(Colegio colegio) {
        this.colegio = colegio;
    }

    public List<Aula> getAulas() {
        return aulas;
    }

    public void setAulas(List<Aula> aulas) {
        this.aulas = aulas;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }
}

