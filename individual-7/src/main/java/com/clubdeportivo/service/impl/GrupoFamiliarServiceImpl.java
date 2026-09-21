package com.clubdeportivo.service.impl;

import com.clubdeportivo.dto.request.GrupoFamiliarRequestDTO;
import com.clubdeportivo.dto.response.GrupoFamiliarResponseDTO;
import com.clubdeportivo.entity.GrupoFamiliar;
import com.clubdeportivo.entity.Socio;
import com.clubdeportivo.mapper.GrupoFamiliarMapper;
import com.clubdeportivo.repository.GrupoFamiliarRepository;
import com.clubdeportivo.repository.SocioRepository;
import com.clubdeportivo.service.GrupoFamiliarService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * =========================================================================================
 * SERVICIO IMPL: GrupoFamiliarServiceImpl
 * =========================================================================================
 * Implementación para la administración de núcleos familiares asociados a un socio titular.
 */
@Service
@Transactional
public class GrupoFamiliarServiceImpl implements GrupoFamiliarService {

    private final GrupoFamiliarRepository grupoFamiliarRepository;
    private final SocioRepository socioRepository;
    private final GrupoFamiliarMapper grupoFamiliarMapper;

    public GrupoFamiliarServiceImpl(GrupoFamiliarRepository grupoFamiliarRepository,
                                   SocioRepository socioRepository,
                                   GrupoFamiliarMapper grupoFamiliarMapper) {
        this.grupoFamiliarRepository = grupoFamiliarRepository;
        this.socioRepository = socioRepository;
        this.grupoFamiliarMapper = grupoFamiliarMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public GrupoFamiliarResponseDTO obtenerGrupoPorId(Long id) {
        return grupoFamiliarRepository.findByIdConIntegrantes(id)
                .map(grupoFamiliarMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el grupo familiar con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public GrupoFamiliarResponseDTO obtenerGrupoPorSocioTitular(Long socioId) {
        return grupoFamiliarRepository.findByTitularId(socioId)
                .map(grupoFamiliarMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("El socio con ID " + socioId + " no tiene grupo familiar asignado."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GrupoFamiliarResponseDTO> obtenerTodosLosGrupos() {
        return grupoFamiliarRepository.findAll().stream()
                .map(grupoFamiliarMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GrupoFamiliarResponseDTO crearGrupoFamiliar(GrupoFamiliarRequestDTO dto) {
        Socio socio = socioRepository.findById(dto.getSocioTitularId())
                .orElseThrow(() -> new IllegalArgumentException("Socio titular no encontrado con ID: " + dto.getSocioTitularId()));

        if (grupoFamiliarRepository.findByTitularId(socio.getId()).isPresent()) {
            throw new IllegalArgumentException("El socio titular ya encabeza un grupo familiar existente.");
        }

        String codigo = "GF-" + socio.getNumeroSocio() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        GrupoFamiliar grupo = new GrupoFamiliar(codigo, socio, dto.getObservaciones());
        GrupoFamiliar guardado = grupoFamiliarRepository.save(grupo);
        socio.setGrupoFamiliar(guardado);

        return grupoFamiliarMapper.toResponseDTO(guardado);
    }
}

