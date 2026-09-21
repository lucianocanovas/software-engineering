package com.clubdeportivo.service;

import com.clubdeportivo.dto.request.RegistroAccesoRequestDTO;
import com.clubdeportivo.dto.response.RegistroAccesoResponseDTO;
import com.clubdeportivo.entity.PuntoDeAcceso;

import java.util.List;

/**
 * =========================================================================================
 * SERVICIO: ControlAccesoService
 * =========================================================================================
 * Supervisa el aforo del club, valida el estado de socios y cuotas familiares antes de
 * habilitar el paso, y registra timestamps de entrada y salida con imagen biométrica facial.
 */
public interface ControlAccesoService {

    RegistroAccesoResponseDTO registrarMovimiento(RegistroAccesoRequestDTO dto);

    List<RegistroAccesoResponseDTO> obtenerUltimosAccesos();

    List<RegistroAccesoResponseDTO> obtenerAccesosPorPersona(Long personaId);

    List<PuntoDeAcceso> obtenerPuntosDeAcceso();
}

