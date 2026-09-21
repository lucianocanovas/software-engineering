package com.clubdeportivo.service;

import com.clubdeportivo.dto.request.CuotaEmisionRequestDTO;
import com.clubdeportivo.dto.response.CuotaResponseDTO;

import java.util.List;

/**
 * =========================================================================================
 * SERVICIO: CuotaService
 * =========================================================================================
 * Operaciones transaccionales para la emisión, cálculo y control de vencimiento de cuotas.
 */
public interface CuotaService {

    CuotaResponseDTO emitirCuota(CuotaEmisionRequestDTO dto);

    CuotaResponseDTO obtenerCuotaPorId(Long id);

    List<CuotaResponseDTO> obtenerCuotasPorSocio(Long socioId);

    List<CuotaResponseDTO> obtenerCuotasPorGrupoFamiliar(Long grupoId);

    List<CuotaResponseDTO> obtenerTodasLasCuotas();

    List<CuotaResponseDTO> obtenerCuotasImpagas(Long socioId);

    void actualizarEstadosVencimiento();
}

