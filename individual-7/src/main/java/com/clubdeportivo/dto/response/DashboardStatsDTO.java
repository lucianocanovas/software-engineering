package com.clubdeportivo.dto.response;

import java.math.BigDecimal;

/**
 * =========================================================================================
 * DTO RESPONSE: DashboardStatsDTO
 * =========================================================================================
 * Métricas ejecutivas y KPIs para las tarjetas visuales de Bootstrap 5 en el panel principal.
 */
public class DashboardStatsDTO {

    private long totalSocios;
    private long sociosAlDia;
    private long sociosMorosos;
    private long accesosHoy;
    private BigDecimal recaudacionTotal;
    private long cuotasPendientes;
    private long cuotasVencidas;
    private long totalGruposFamiliares;

    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public long getTotalSocios() {
        return totalSocios;
    }

    public void setTotalSocios(long totalSocios) {
        this.totalSocios = totalSocios;
    }

    public long getSociosAlDia() {
        return sociosAlDia;
    }

    public void setSociosAlDia(long sociosAlDia) {
        this.sociosAlDia = sociosAlDia;
    }

    public long getSociosMorosos() {
        return sociosMorosos;
    }

    public void setSociosMorosos(long sociosMorosos) {
        this.sociosMorosos = sociosMorosos;
    }

    public long getAccesosHoy() {
        return accesosHoy;
    }

    public void setAccesosHoy(long accesosHoy) {
        this.accesosHoy = accesosHoy;
    }

    public BigDecimal getRecaudacionTotal() {
        return recaudacionTotal;
    }

    public void setRecaudacionTotal(BigDecimal recaudacionTotal) {
        this.recaudacionTotal = recaudacionTotal;
    }

    public long getCuotasPendientes() {
        return cuotasPendientes;
    }

    public void setCuotasPendientes(long cuotasPendientes) {
        this.cuotasPendientes = cuotasPendientes;
    }

    public long getCuotasVencidas() {
        return cuotasVencidas;
    }

    public void setCuotasVencidas(long cuotasVencidas) {
        this.cuotasVencidas = cuotasVencidas;
    }

    public long getTotalGruposFamiliares() {
        return totalGruposFamiliares;
    }

    public void setTotalGruposFamiliares(long totalGruposFamiliares) {
        this.totalGruposFamiliares = totalGruposFamiliares;
    }
}
