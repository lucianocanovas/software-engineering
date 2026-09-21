package com.clubdeportivo.dto.request;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * =========================================================================================
 * DTO REQUEST: CuotaEmisionRequestDTO
 * =========================================================================================
 * Formulario para emisión masiva o individual de cuotas mensuales familiares.
 */
public class CuotaEmisionRequestDTO {

    @NotNull(message = "Debe indicar el socio titular")
    private Long socioTitularId;

    @NotBlank(message = "El periodo es obligatorio (Ej: 2026-09)")
    @Pattern(regexp = "\\d{4}-\\d{2}", message = "El formato de periodo debe ser YYYY-MM")
    private String periodo;

    @NotNull(message = "El monto base es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto base debe ser positivo")
    private BigDecimal montoBase;

    @DecimalMin(value = "0.00", message = "El recargo no puede ser negativo")
    private BigDecimal montoRecargo = BigDecimal.ZERO;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaVencimiento;

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getSocioTitularId() {
        return socioTitularId;
    }

    public void setSocioTitularId(Long socioTitularId) {
        this.socioTitularId = socioTitularId;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public BigDecimal getMontoBase() {
        return montoBase;
    }

    public void setMontoBase(BigDecimal montoBase) {
        this.montoBase = montoBase;
    }

    public BigDecimal getMontoRecargo() {
        return montoRecargo;
    }

    public void setMontoRecargo(BigDecimal montoRecargo) {
        this.montoRecargo = montoRecargo;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }
}
