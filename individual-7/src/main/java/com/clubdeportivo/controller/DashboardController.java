package com.clubdeportivo.controller;

import com.clubdeportivo.dto.response.DashboardStatsDTO;
import com.clubdeportivo.service.ControlAccesoService;
import com.clubdeportivo.service.CuotaService;
import com.clubdeportivo.service.DashboardService;
import com.clubdeportivo.service.PagoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * =========================================================================================
 * CONTROLADOR: DashboardController
 * =========================================================================================
 * Presenta el panel de control ejecutivo (Admin Dashboard) estilizado con Bootstrap 5
 * (estilo ThemeWagon), desplegando métricas KPI, alertas de cuotas vencidas y accesos recientes.
 */
@Controller
public class DashboardController {

    private final DashboardService dashboardService;
    private final ControlAccesoService controlAccesoService;
    private final PagoService pagoService;
    private final CuotaService cuotaService;

    public DashboardController(DashboardService dashboardService,
                               ControlAccesoService controlAccesoService,
                               PagoService pagoService,
                               CuotaService cuotaService) {
        this.dashboardService = dashboardService;
        this.controlAccesoService = controlAccesoService;
        this.pagoService = pagoService;
        this.cuotaService = cuotaService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Actualiza estados de vencimiento de cuotas al cargar
        cuotaService.actualizarEstadosVencimiento();

        DashboardStatsDTO stats = dashboardService.obtenerEstadisticas();
        model.addAttribute("stats", stats);
        model.addAttribute("ultimosAccesos", controlAccesoService.obtenerUltimosAccesos());
        model.addAttribute("ultimosPagos", pagoService.obtenerTodosLosPagos());
        model.addAttribute("puntosAcceso", controlAccesoService.obtenerPuntosDeAcceso());

        return "dashboard/index";
    }
}
