package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.RolUsuario;
import jakarta.persistence.*;

/**
 * =========================================================================================
 * ENTIDAD: Usuario
 * =========================================================================================
 * Modela las credenciales y el perfil de acceso seguro al sistema (Spring Security):
 * - Contraseña encriptada con algoritmo hash BCrypt.
 * - Roles asignados: ROLE_ADMIN, ROLE_OPERADOR, ROLE_SOCIO.
 * - Opcionalmente vinculado a un Socio para autoservicio de consulta de cuotas y pagos.
 */
@Entity
@Table(name = "usuarios")
public class Usuario extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 60)
    private String username;

    @Column(name = "password", nullable = false, length = 120)
    private String password;

    @Column(name = "nombre_completo", nullable = false, length = 100)
    private String nombreCompleto;

    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false, length = 30)
    private RolUsuario rol = RolUsuario.ROLE_OPERADOR;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    /**
     * Vinculación opcional con un Socio para portales de socios.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id")
    private Socio socio;

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public Usuario() {
        this.activo = true;
    }

    public Usuario(String username, String password, String nombreCompleto, RolUsuario rol) {
        this();
        this.username = username;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.rol = (rol != null) ? rol : RolUsuario.ROLE_OPERADOR;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public RolUsuario getRol() {
        return rol;
    }

    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }
}
