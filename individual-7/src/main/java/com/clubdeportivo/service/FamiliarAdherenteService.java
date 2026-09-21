package com.clubdeportivo.service;

import com.clubdeportivo.dto.request.FamiliarAdherenteRequestDTO;
import com.clubdeportivo.dto.response.FamiliarAdherenteResponseDTO;

import java.util.List;

/**
 * =========================================================================================
 * SERVICIO: FamiliarAdherenteService
 * =========================================================================================
 * Operaciones para la gestión de adherentes dentro del grupo familiar del socio.
 */
public interface FamiliarAdherenteService {

    FamiliarAdherenteResponseDTO agregarAdherente(FamiliarAdherenteRequestDTO dto);

    FamiliarAdherenteResponseDTO obtenerAdherentePorId(Long id);

    List<FamiliarAdherenteResponseDTO> listarPorGrupoFamiliar(Long grupoFamiliarId);

    void eliminarAdherente(Long id);
}

