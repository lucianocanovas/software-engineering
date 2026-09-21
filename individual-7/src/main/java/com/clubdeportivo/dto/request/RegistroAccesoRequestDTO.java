package com.clubdeportivo.dto.request;

import com.clubdeportivo.entity.enums.TipoMovimiento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * =========================================================================================
 * DTO REQUEST: RegistroAccesoRequestDTO
 * =========================================================================================
 * Contrato de entrada para registrar el ingreso o egreso de una persona a través de un molinete.
 */
public class RegistroAccesoRequestDTO {

    @NotBlank(message = "Debe ingresar el DNI de la persona o escanear su carnet")
    private String dniPersona;

    @NotNull(message = "Debe seleccionar el punto de acceso (molinete/puerta)")
    private Long puntoDeAccesoId;

    @NotNull(message = "Debe indicar el sentido del movimiento (Entrada o Salida)")
    private TipoMovimiento tipoMovimiento = TipoMovimiento.ENTRADA;

    private String observacion;

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public String getDniPersona() {
        return dniPersona;
    }

    public void setDniPersona(String dniPersona) {
        this.dniPersona = dniPersona;
    }

    public Long getPuntoDeAccesoId() {
        return puntoDeAccesoId;
    }

    public void setPuntoDeAccesoId(Long puntoDeAccesoId) {
        this.puntoDeAccesoId = puntoDeAccesoId;
    }

    public TipoMovimiento getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(TipoMovimiento tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
