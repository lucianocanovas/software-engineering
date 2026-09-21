package com.clubdeportivo.dto.response;

import com.clubdeportivo.entity.enums.TipoMovimiento;
import java.time.LocalDateTime;

/**
 * =========================================================================================
 * DTO RESPONSE: RegistroAccesoResponseDTO
 * =========================================================================================
 * Datos proyectados para la monitorización en vivo del molinete de acceso y registro histórico.
 */
public class RegistroAccesoResponseDTO {

    private Long id;
    private Long personaId;
    private String personaNombreCompleto;
    private String personaDni;
    private String tipoPersona; // Socio, Familiar Adherente, Empleado, Profesor
    private String rutaFoto;
    private boolean habilitado;
    private String motivoBloqueo;
    private Long puntoDeAccesoId;
    private String puntoDeAccesoNombre;
    private String puntoDeAccesoUbicacion;
    private LocalDateTime fechaHoraEntrada;
    private LocalDateTime fechaHoraSalida;
    private TipoMovimiento tipoMovimiento;
    private long permanenciaMinutos;
    private String observacion;

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPersonaId() {
        return personaId;
    }

    public void setPersonaId(Long personaId) {
        this.personaId = personaId;
    }

    public String getPersonaNombreCompleto() {
        return personaNombreCompleto;
    }

    public void setPersonaNombreCompleto(String personaNombreCompleto) {
        this.personaNombreCompleto = personaNombreCompleto;
    }

    public String getPersonaDni() {
        return personaDni;
    }

    public void setPersonaDni(String personaDni) {
        this.personaDni = personaDni;
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        this.tipoPersona = tipoPersona;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getMotivoBloqueo() {
        return motivoBloqueo;
    }

    public void setMotivoBloqueo(String motivoBloqueo) {
        this.motivoBloqueo = motivoBloqueo;
    }

    public Long getPuntoDeAccesoId() {
        return puntoDeAccesoId;
    }

    public void setPuntoDeAccesoId(Long puntoDeAccesoId) {
        this.puntoDeAccesoId = puntoDeAccesoId;
    }

    public String getPuntoDeAccesoNombre() {
        return puntoDeAccesoNombre;
    }

    public void setPuntoDeAccesoNombre(String puntoDeAccesoNombre) {
        this.puntoDeAccesoNombre = puntoDeAccesoNombre;
    }

    public String getPuntoDeAccesoUbicacion() {
        return puntoDeAccesoUbicacion;
    }

    public void setPuntoDeAccesoUbicacion(String puntoDeAccesoUbicacion) {
        this.puntoDeAccesoUbicacion = puntoDeAccesoUbicacion;
    }

    public LocalDateTime getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(LocalDateTime fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public LocalDateTime getFechaHoraSalida() {
        return fechaHoraSalida;
    }

    public void setFechaHoraSalida(LocalDateTime fechaHoraSalida) {
        this.fechaHoraSalida = fechaHoraSalida;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public long getPermanenciaMinutos() {
        return permanenciaMinutos;
    }

    public void setPermanenciaMinutos(long permanenciaMinutos) {
        this.permanenciaMinutos = permanenciaMinutos;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
