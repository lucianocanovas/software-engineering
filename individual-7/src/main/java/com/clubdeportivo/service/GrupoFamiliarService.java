package com.clubdeportivo.service;

import com.clubdeportivo.dto.request.GrupoFamiliarRequestDTO;
import com.clubdeportivo.dto.response.GrupoFamiliarResponseDTO;

import java.util.List;

/**
 * =========================================================================================
 * SERVICIO: GrupoFamiliarService
 * =========================================================================================
 * Gestión y consulta de suscripciones familiares agrupadas.
 */
public interface GrupoFamiliarService {

    GrupoFamiliarResponseDTO obtenerGrupoPorId(Long id);

    GrupoFamiliarResponseDTO obtenerGrupoPorSocioTitular(Long socioId);

    List<GrupoFamiliarResponseDTO> obtenerTodosLosGrupos();

    GrupoFamiliarResponseDTO crearGrupoFamiliar(GrupoFamiliarRequestDTO dto);
}

