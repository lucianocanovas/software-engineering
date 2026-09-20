package com.colegio.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * =========================================================================================
 * ENTIDAD: Colegio
 * =========================================================================================
 * Representa la institución escolar.
 *
 * RELACIÓN UML IMPLEMENTADA:
 * - Composición con Grado (1 Colegio contiene 1..* Grados):
 *   Los grados existen única y exclusivamente dentro de un colegio concreto.
 *   Se modela con CascadeType.ALL y orphanRemoval = true: si un colegio se elimina,
 *   todos sus grados constitutivos se destruyen en cascada.
 */
@Entity
@Table(name = "colegios")
public class Colegio extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "telefono", length = 50)
    private String telefono;

    /**
     * COMPOSICIÓN UML (1..* Grados pertenecientes al Colegio):
     * orphanRemoval = true garantiza que ningún Grado quede huérfano en la base de datos.
     */
    @OneToMany(mappedBy = "colegio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grado> grados = new ArrayList<>();

    public Colegio() {
    }

    public Colegio(String nombre, String direccion, String telefono) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    public void agregarGrado(Grado grado) {
        grados.add(grado);
        grado.setColegio(this);
    }

    public void removerGrado(Grado grado) {
        grados.remove(grado);
        grado.setColegio(null);
    }

    // Getters y Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Grado> getGrados() {
        return grados;
    }

    public void setGrados(List<Grado> grados) {
        this.grados = grados;
    }
}

