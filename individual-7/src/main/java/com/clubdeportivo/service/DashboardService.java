package com.clubdeportivo.service;

import com.clubdeportivo.dto.response.DashboardStatsDTO;

/**
 * =========================================================================================
 * SERVICIO: DashboardService
 * =========================================================================================
 * Genera estadísticas operativas y financieras consolidando datos de socios, pagos y accesos.
 */
public interface DashboardService {

    DashboardStatsDTO obtenerEstadisticas();
}
