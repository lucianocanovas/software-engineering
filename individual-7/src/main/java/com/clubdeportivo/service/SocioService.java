package com.clubdeportivo.service;

import com.clubdeportivo.dto.request.SocioRequestDTO;
import com.clubdeportivo.dto.response.SocioResponseDTO;

import java.util.List;

/**
 * =========================================================================================
 * SERVICIO: SocioService (Interfaz de Negocio)
 * =========================================================================================
 * Define las operaciones transaccionales para la administración integral de socios titulares.
 */
public interface SocioService {

    SocioResponseDTO crearSocio(SocioRequestDTO dto);

    SocioResponseDTO actualizarSocio(Long id, SocioRequestDTO dto);

    SocioResponseDTO obtenerSocioPorId(Long id);

    List<SocioResponseDTO> obtenerTodosLosSocios();

    List<SocioResponseDTO> buscarSocios(String query);

    void darDeBajaSocio(Long id);

    void reactivarSocio(Long id);
}

