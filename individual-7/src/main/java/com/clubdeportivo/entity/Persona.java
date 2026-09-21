package com.clubdeportivo.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================================
 * CLASE ABSTRACTA / ENTIDAD: Persona
 * =========================================================================================
 * Representa el nodo raíz de la jerarquía de generalización/especialización de individuos:
 * - Demuestra la relación UML de HERENCIA: Es la clase padre abstracta de Socio,
 *   FamiliarAdherente y Empleado.
 * - Demuestra la relación UML de COMPOSICIÓN con Fotografia: Cada persona posee exactamente
 *   una fotografía biométrica que no puede existir sin ella (CascadeType.ALL, orphanRemoval=true).
 * - Demuestra la relación UML de ASOCIACIÓN con RegistroAcceso: Una persona genera accesos.
 *
 * Estrategia de Mapeo ORM:
 * - @Inheritance(strategy = InheritanceType.JOINED): Crea una tabla base "personas" con los
 *   campos comunes y tablas derivadas ("socios", "familiares_adherentes", "empleados") con
 *   sus claves foráneas correspondientes. Favorece la integridad referencial en MySQL.
 */
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dni", nullable = false, unique = true, length = 20)
    private String dni;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 80)
    private String apellido;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    /**
     * RELACIÓN DE COMPOSICIÓN (1 posee 1):
     * La fotografía de reconocimiento facial está fuertemente acoplada a la persona.
     * Al persistir o eliminar la persona, la foto se propaga o elimina automáticamente.
     */
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "fotografia_id", referencedColumnName = "id")
    private Fotografia fotografia;

    /**
     * RELACIÓN DE ASOCIACIÓN (1 genera 0..*):
     * Historial de entradas y salidas registradas por esta persona en los molinetes del club.
     */
    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RegistroAcceso> registrosAcceso = new ArrayList<>();

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public Persona() {
        this.activo = true;
    }

    public Persona(String dni, String nombre, String apellido, LocalDate fechaNacimiento, String telefono, String email) {
        this();
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.telefono = telefono;
        this.email = email;
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Calcula la edad cronológica exacta de la persona a la fecha actual.
     * @return Años cumplidos.
     */
    public int calcularEdad() {
        if (this.fechaNacimiento == null) {
            return 0;
        }
        return Period.between(this.fechaNacimiento, LocalDate.now()).getYears();
    }

    /**
     * Retorna la concatenación legible del nombre completo: "Apellido, Nombre".
     */
    public String obtenerNombreCompleto() {
        return (this.apellido != null ? this.apellido.toUpperCase() : "") + ", " +
               (this.nombre != null ? this.nombre : "");
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Fotografia getFotografia() {
        return fotografia;
    }

    public void setFotografia(Fotografia fotografia) {
        this.fotografia = fotografia;
        if (fotografia != null) {
            fotografia.setPersona(this);
        }
    }

    public List<RegistroAcceso> getRegistrosAcceso() {
        return registrosAcceso;
    }

    public void setRegistrosAcceso(List<RegistroAcceso> registrosAcceso) {
        this.registrosAcceso = registrosAcceso;
    }
}
