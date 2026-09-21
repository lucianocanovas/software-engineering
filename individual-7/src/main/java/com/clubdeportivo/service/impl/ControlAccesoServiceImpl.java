package com.clubdeportivo.service.impl;

import com.clubdeportivo.dto.request.RegistroAccesoRequestDTO;
import com.clubdeportivo.dto.response.RegistroAccesoResponseDTO;
import com.clubdeportivo.entity.*;
import com.clubdeportivo.entity.enums.TipoMovimiento;
import com.clubdeportivo.mapper.RegistroAccesoMapper;
import com.clubdeportivo.repository.PersonaRepository;
import com.clubdeportivo.repository.PuntoDeAccesoRepository;
import com.clubdeportivo.repository.RegistroAccesoRepository;
import com.clubdeportivo.service.ControlAccesoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * =========================================================================================
 * SERVICIO IMPL: ControlAccesoServiceImpl
 * =========================================================================================
 * Lógica de negocio para molinetes y puntos de control de acceso:
 * - Valida la condición de cuotas al día del socio titular y sus adherentes.
 * - Registra la marca horaria de entrada y salida, permitiendo el cálculo de permanencia.
 * - Verifica el estado operativo de los puntos de acceso y la disponibilidad de biometría facial.
 */
@Service
@Transactional
public class ControlAccesoServiceImpl implements ControlAccesoService {

    private final RegistroAccesoRepository registroAccesoRepository;
    private final PersonaRepository personaRepository;
    private final PuntoDeAccesoRepository puntoDeAccesoRepository;
    private final RegistroAccesoMapper registroAccesoMapper;

    public ControlAccesoServiceImpl(RegistroAccesoRepository registroAccesoRepository,
                                    PersonaRepository personaRepository,
                                    PuntoDeAccesoRepository puntoDeAccesoRepository,
                                    RegistroAccesoMapper registroAccesoMapper) {
        this.registroAccesoRepository = registroAccesoRepository;
        this.personaRepository = personaRepository;
        this.puntoDeAccesoRepository = puntoDeAccesoRepository;
        this.registroAccesoMapper = registroAccesoMapper;
    }

    @Override
    public RegistroAccesoResponseDTO registrarMovimiento(RegistroAccesoRequestDTO dto) {
        Persona persona = personaRepository.findByDni(dto.getDniPersona().trim())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ninguna persona registrada con el DNI: " + dto.getDniPersona()));

        PuntoDeAcceso punto = puntoDeAccesoRepository.findById(dto.getPuntoDeAccesoId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el punto de acceso con ID: " + dto.getPuntoDeAccesoId()));

        if (!punto.habilitarPaso()) {
            throw new IllegalStateException("El punto de acceso '" + punto.getNombre() + "' no se encuentra operativo (Estado: " + punto.getEstado() + ").");
        }

        // Evaluación de habilitación de acceso según cuotas y estado
        StringBuilder observaciones = new StringBuilder();
        if (dto.getObservacion() != null && !dto.getObservacion().isBlank()) {
            observaciones.append(dto.getObservacion()).append(". ");
        }

        boolean habilitado = true;
        if (!Boolean.TRUE.equals(persona.getActivo())) {
            habilitado = false;
            observaciones.append("[ALERTA: Persona dada de baja o inactiva]. ");
        }

        if (persona instanceof Socio socio && !socio.estaAlDia()) {
            habilitado = false;
            observaciones.append("[DENEGADO: Socio con cuotas impagas]. ");
        } else if (persona instanceof FamiliarAdherente adherente) {
            boolean titularAlDia = adherente.getGrupoFamiliar() != null &&
                                   adherente.getGrupoFamiliar().getTitular() != null &&
                                   adherente.getGrupoFamiliar().getTitular().estaAlDia();
            if (!titularAlDia) {
                habilitado = false;
                observaciones.append("[DENEGADO: Cuota familiar pendiente]. ");
            }
        }

        // Verificación de existencia de foto de rostro
        if (persona.getFotografia() == null) {
            observaciones.append("[ADVERTENCIA: Sin fotografía facial registrada]. ");
        }

        RegistroAcceso registro = new RegistroAcceso();
        registro.setPersona(persona);
        registro.setPuntoDeAcceso(punto);
        registro.setTipoMovimiento(dto.getTipoMovimiento());
        registro.setObservacion(observaciones.toString().trim());

        if (dto.getTipoMovimiento() == TipoMovimiento.ENTRADA) {
            registro.setFechaHoraEntrada(LocalDateTime.now());
        } else {
            // Si es salida, intentar asociar con la última entrada pendiente para calcular permanencia
            List<RegistroAcceso> entradasAbiertas = registroAccesoRepository.findUltimaEntradaSinSalida(persona.getId(), TipoMovimiento.ENTRADA);
            if (!entradasAbiertas.isEmpty()) {
                RegistroAcceso entrada = entradasAbiertas.get(0);
                entrada.setFechaHoraSalida(LocalDateTime.now());
                registroAccesoRepository.save(entrada);
                return registroAccesoMapper.toResponseDTO(entrada);
            } else {
                registro.setFechaHoraSalida(LocalDateTime.now());
            }
        }

        RegistroAcceso guardado = registroAccesoRepository.save(registro);
        RegistroAccesoResponseDTO responseDTO = registroAccesoMapper.toResponseDTO(guardado);
        responseDTO.setHabilitado(habilitado);

        return responseDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroAccesoResponseDTO> obtenerUltimosAccesos() {
        return registroAccesoRepository.findTop20ByOrderByFechaHoraEntradaDesc().stream()
                .map(registroAccesoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RegistroAccesoResponseDTO> obtenerAccesosPorPersona(Long personaId) {
        return registroAccesoRepository.findByPersonaIdOrderByFechaHoraEntradaDesc(personaId).stream()
                .map(registroAccesoMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PuntoDeAcceso> obtenerPuntosDeAcceso() {
        return puntoDeAccesoRepository.findAll();
    }
}

