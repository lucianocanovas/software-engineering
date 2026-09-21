package com.clubdeportivo.service.impl;

import com.clubdeportivo.dto.response.DashboardStatsDTO;
import com.clubdeportivo.entity.Socio;
import com.clubdeportivo.entity.enums.EstadoCuota;
import com.clubdeportivo.entity.enums.EstadoPago;
import com.clubdeportivo.entity.enums.TipoMovimiento;
import com.clubdeportivo.repository.*;
import com.clubdeportivo.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * =========================================================================================
 * SERVICIO IMPL: DashboardServiceImpl
 * =========================================================================================
 * Calcula las métricas para los widgets y tarjetas visuales del panel de control de administración.
 */
@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final SocioRepository socioRepository;
    private final CuotaRepository cuotaRepository;
    private final PagoRepository pagoRepository;
    private final RegistroAccesoRepository registroAccesoRepository;
    private final GrupoFamiliarRepository grupoFamiliarRepository;

    public DashboardServiceImpl(SocioRepository socioRepository,
                                CuotaRepository cuotaRepository,
                                PagoRepository pagoRepository,
                                RegistroAccesoRepository registroAccesoRepository,
                                GrupoFamiliarRepository grupoFamiliarRepository) {
        this.socioRepository = socioRepository;
        this.cuotaRepository = cuotaRepository;
        this.pagoRepository = pagoRepository;
        this.registroAccesoRepository = registroAccesoRepository;
        this.grupoFamiliarRepository = grupoFamiliarRepository;
    }

    @Override
    public DashboardStatsDTO obtenerEstadisticas() {
        DashboardStatsDTO stats = new DashboardStatsDTO();

        List<Socio> socios = socioRepository.findAll();
        stats.setTotalSocios(socios.size());

        long alDia = socios.stream().filter(Socio::estaAlDia).count();
        stats.setSociosAlDia(alDia);
        stats.setSociosMorosos(socios.size() - alDia);

        stats.setTotalGruposFamiliares(grupoFamiliarRepository.count());
        stats.setCuotasPendientes(cuotaRepository.countByEstado(EstadoCuota.PENDIENTE));
        stats.setCuotasVencidas(cuotaRepository.countByEstado(EstadoCuota.VENCIDA));

        BigDecimal recaudacion = pagoRepository.sumTotalRecaudadoPorEstado(EstadoPago.COMPLETADO);
        stats.setRecaudacionTotal(recaudacion != null ? recaudacion : BigDecimal.ZERO);

        LocalDateTime inicioHoy = LocalDate.now().atStartOfDay();
        LocalDateTime finHoy = LocalDate.now().atTime(23, 59, 59);
        stats.setAccesosHoy(registroAccesoRepository.countByTipoMovimientoAndFechaHoraEntradaBetween(TipoMovimiento.ENTRADA, inicioHoy, finHoy));

        return stats;
    }
}

