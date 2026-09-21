package com.clubdeportivo.entity;

import com.clubdeportivo.entity.enums.EstadoPuntoAcceso;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================================
 * ENTIDAD: PuntoDeAcceso
 * =========================================================================================
 * Representa los molinetes, torniquetes, lectores biométricos o portones vehiculares del club:
 * - Demuestra relación de ASOCIACIÓN con RegistroAcceso: En un punto de acceso se producen
 *   múltiples registros de paso (1 a 0..*).
 * - Método de dominio:
 *   - habilitarPaso(): Evalúa si el punto de acceso físico está en condiciones técnicas operativas
 *     para desbloquear el molinete.
 */
@Entity
@Table(name = "puntos_de_acceso")
public class PuntoDeAcceso extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "ubicacion", nullable = false, length = 120)
    private String ubicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 30)
    private EstadoPuntoAcceso estado;

    @Column(name = "ip_lector", length = 45)
    private String ipLector;

    /**
     * Registros de movimientos físicos producidos en este punto.
     */
    @OneToMany(mappedBy = "puntoDeAcceso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RegistroAcceso> registros = new ArrayList<>();

    // =====================================================================================
    // CONSTRUCTORES
    // =====================================================================================

    public PuntoDeAcceso() {
        this.estado = EstadoPuntoAcceso.OPERATIVO;
    }

    public PuntoDeAcceso(String nombre, String ubicacion, EstadoPuntoAcceso estado, String ipLector) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.estado = (estado != null) ? estado : EstadoPuntoAcceso.OPERATIVO;
        this.ipLector = ipLector;
    }

    // =====================================================================================
    // MÉTODOS DE NEGOCIO / DOMINIO
    // =====================================================================================

    /**
     * Valida si el hardware del molinete responde y se encuentra en estado OPERATIVO.
     */
    public boolean habilitarPaso() {
        return this.estado == EstadoPuntoAcceso.OPERATIVO;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public EstadoPuntoAcceso getEstado() {
        return estado;
    }

    public void setEstado(EstadoPuntoAcceso estado) {
        this.estado = estado;
    }

    public String getIpLector() {
        return ipLector;
    }

    public void setIpLector(String ipLector) {
        this.ipLector = ipLector;
    }

    public List<RegistroAcceso> getRegistros() {
        return registros;
    }

    public void setRegistros(List<RegistroAcceso> registros) {
        this.registros = registros;
    }
}
