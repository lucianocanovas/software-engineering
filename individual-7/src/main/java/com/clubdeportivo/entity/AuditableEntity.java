package com.clubdeportivo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * =========================================================================================
 * CLASE ABSTRACTA: AuditableEntity (Superclase Mapeada)
 * =========================================================================================
 * Proporciona trazabilidad y auditoría automática para todas las entidades del sistema:
 * - @MappedSuperclass: Indica a JPA/Hibernate que esta clase no tiene tabla propia, pero sus
 *   atributos persistentes se heredan en las tablas hijas.
 * - @EntityListeners(AuditingEntityListener.class): Activa el interceptor de Spring Data JPA
 *   que captura eventos de ciclo de vida (PrePersist, PreUpdate).
 * - @CreatedDate / @LastModifiedDate: Estampas de tiempo UTC gestionadas automáticamente.
 * - @CreatedBy / @LastModifiedBy: Identificador del usuario autenticado en Spring Security
 *   provisto por el componente AuditorAware.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

    /**
     * Fecha y hora exacta de inserción inicial del registro en base de datos.
     * Inmutable: updatable = false previene sobrescritura en operaciones de actualización.
     */
    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    /**
     * Fecha y hora de la última modificación sufrida por la entidad.
     */
    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    /**
     * Nombre de usuario (username) del operador o proceso que dio de alta el registro.
     */
    @CreatedBy
    @Column(name = "creado_por", length = 80, updatable = false)
    private String creadoPor;

    /**
     * Nombre de usuario que ejecutó la última actualización del registro.
     */
    @LastModifiedBy
    @Column(name = "modificado_por", length = 80)
    private String modificadoPor;

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getCreadoPor() {
        return creadoPor;
    }

    public void setCreadoPor(String creadoPor) {
        this.creadoPor = creadoPor;
    }

    public String getModificadoPor() {
        return modificadoPor;
    }

    public void setModificadoPor(String modificadoPor) {
        this.modificadoPor = modificadoPor;
    }
}
