package com.colegio.model;

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
 * CLASE BASE DE AUDITORÍA: AuditableEntity
 * =========================================================================================
 * Anotada con @MappedSuperclass para que sus atributos sean heredados y persistidos en las
 * tablas correspondientes a las entidades hijas sin crear una tabla separada para esta clase.
 *
 * Utiliza los oyentes de auditoría de Spring Data JPA (@EntityListeners(AuditingEntityListener.class))
 * para capturar de manera completamente transparente y automatizada:
 * 1. Fecha y hora exacta de creación del registro.
 * 2. Fecha y hora de última modificación.
 * 3. Usuario del sistema autenticado que realizó el alta.
 * 4. Usuario del sistema autenticado que realizó la última actualización.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity {

    /**
     * Marca temporal de creación gestionada automáticamente por el framework.
     */
    @CreatedDate
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    /**
     * Marca temporal de la última actualización realizada.
     */
    @LastModifiedDate
    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    /**
     * Identificador (correo / username) del usuario autenticado que creó el registro.
     */
    @CreatedBy
    @Column(name = "creado_por", updatable = false, length = 100)
    private String creadoPor;

    /**
     * Identificador del último usuario que modificó el registro.
     */
    @LastModifiedBy
    @Column(name = "modificado_por", length = 100)
    private String modificadoPor;

    // ===================================================================
    // GETTERS Y SETTERS
    // ===================================================================

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

