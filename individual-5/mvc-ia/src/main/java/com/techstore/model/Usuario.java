package com.techstore.model;

import jakarta.persistence.*;

/**
 * =============================================================================
 * CAPA MODELO / ORM: CLASE ABSTRACTA Usuario
 * =============================================================================
 * RELACIÓN UML REPRESENTADA: HERENCIA (Generalización)
 * -----------------------------------------------------------------------------
 * En el diseño orientado a objetos y UML, la relación de HERENCIA permite definir
 * una clase base abstracta que encapsula atributos y comportamientos comunes
 * compartidos por todos los actores que acceden al sistema informático.
 *
 * ESTRATEGIA JPA / ORM:
 * @Entity: Marca esta clase como entidad persistible en la base de datos SQLite.
 * @Table(name = "usuarios"): Mapea la clase a la tabla relacional 'usuarios'.
 * @Inheritance(strategy = InheritanceType.SINGLE_TABLE):
 *   Optamos por la estrategia de tabla única (Single Table), la cual almacena toda
 *   la jerarquía de clases (Usuario, Administrador, Vendedor, EncargadoCompras)
 *   en una sola tabla SQLite. Esto optimiza drásticamente las consultas (SELECT)
 *   al no requerir JOINs costosos entre múltiples tablas en bases de datos embebidas.
 * @DiscriminatorColumn(name = "rol", discriminatorType = DiscriminatorType.STRING):
 *   Define la columna en la base de datos que discrimina el subtipo concreto instanciado.
 */
@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "rol", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_usuario", unique = true, nullable = false, length = 50)
    private String nombreUsuario;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nombre", nullable = false, length = 60)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 60)
    private String apellido;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    // Constructor por defecto requerido obligatoriamente por el estándar JPA/Hibernate
    public Usuario() {
    }

    public Usuario(String nombreUsuario, String password, String nombre, String apellido, String email) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.activo = true;
    }

    /**
     * Retorna el rol o tipo específico del usuario (ej: ADMIN, VENDEDOR, COMPRAS)
     * implementado por cada subclase concreta.
     */
    public abstract String getRol();

    /**
     * Método de autenticación lógica solicitado en el diagrama de clases:
     * Valida si las credenciales provistas coinciden y la cuenta se encuentra activa.
     */
    public boolean login(String claveIngresada) {
        return this.activo && this.password != null && this.password.equals(claveIngresada);
    }

    /**
     * Cierre de sesión de usuario solicitado en el diagrama de clases.
     */
    public void logout() {
        // En una arquitectura web orientada a sesiones, el logout invalida la sesión HTTP.
    }

    // --- Getters y Setters con encapsulamiento ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getNombreCompleto() {
        return (nombre != null ? nombre : "") + " " + (apellido != null ? apellido : "");
    }
}

