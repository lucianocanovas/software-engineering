package com.colegio.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

/**
 * =========================================================================================
 * ENTIDAD RAÍZ DE HERENCIA: Persona
 * =========================================================================================
 * Representa la generalización en la jerarquía de herencia UML:
 *               Persona (Superclase abstracta)
 *               ▲             ▲
 *               │             │
 *          Profesor        Alumno
 *
 * Mapeo ORM:
 * - Estrategia InheritanceType.JOINED: Se genera la tabla 'personas' con las columnas comunes
 *   (id, dni, nombre, apellido, email, sexo, fecha_nacimiento, password, rol) y tablas hijas
 *   'profesores' y 'alumnos' que se unen por clave foránea referenciando a 'personas.id'.
 * - El atributo 'email' funge como identificador único (username) para la autenticación en
 *   Spring Security.
 * - Hereda de AuditableEntity para garantizar el registro de auditoría (quién y cuándo creó/modificó).
 */
@Entity
@Table(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "dni", length = 20, unique = true)
    private String dni;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 80)
    private String apellido;

    /**
     * El correo personal del docente/usuario utilizado como credencial de ingreso al sistema.
     */
    @Column(name = "email", nullable = false, unique = true, length = 120)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", length = 20)
    private Sexo sexo;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    /**
     * Contraseña almacenada en formato Hash mediante BCryptPasswordEncoder.
     */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 30)
    private Rol rol = Rol.ROLE_DOCENTE;

    // Constructores
    public Persona() {
    }

    public Persona(String dni, String nombre, String apellido, String email, Sexo sexo,
                   LocalDate fechaNacimiento, String password, Rol rol) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.sexo = sexo;
        this.fechaNacimiento = fechaNacimiento;
        this.password = password;
        this.rol = rol;
    }

    /**
     * Método de conveniencia para obtener el nombre y apellido formateados.
     */
    public String getNombreCompleto() {
        return (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
    }

    // ===================================================================
    // GETTERS Y SETTERS
    // ===================================================================

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}

