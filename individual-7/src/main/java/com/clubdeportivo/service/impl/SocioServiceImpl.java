package com.clubdeportivo.service.impl;

import com.clubdeportivo.dto.request.SocioRequestDTO;
import com.clubdeportivo.dto.response.SocioResponseDTO;
import com.clubdeportivo.entity.GrupoFamiliar;
import com.clubdeportivo.entity.Socio;
import com.clubdeportivo.mapper.SocioMapper;
import com.clubdeportivo.repository.GrupoFamiliarRepository;
import com.clubdeportivo.repository.SocioRepository;
import com.clubdeportivo.service.SocioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * =========================================================================================
 * SERVICIO IMPL: SocioServiceImpl
 * =========================================================================================
 * Implementación de la lógica de negocio para Socios Titulares:
 * - Gestiona la creación de socios y la generación automática de su Grupo Familiar inicial.
 * - Mantiene el principio de unicidad en número de socio y DNI.
 * - Desacopla las entidades de la capa web utilizando DTOs en todos los contratos públicos.
 */
@Service
@Transactional
public class SocioServiceImpl implements SocioService {

    private final SocioRepository socioRepository;
    private final GrupoFamiliarRepository grupoFamiliarRepository;
    private final SocioMapper socioMapper;

    public SocioServiceImpl(SocioRepository socioRepository,
                            GrupoFamiliarRepository grupoFamiliarRepository,
                            SocioMapper socioMapper) {
        this.socioRepository = socioRepository;
        this.grupoFamiliarRepository = grupoFamiliarRepository;
        this.socioMapper = socioMapper;
    }

    @Override
    public SocioResponseDTO crearSocio(SocioRequestDTO dto) {
        if (socioRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("Ya existe una persona registrada con el DNI: " + dto.getDni());
        }
        if (socioRepository.existsByNumeroSocio(dto.getNumeroSocio())) {
            throw new IllegalArgumentException("El número de socio " + dto.getNumeroSocio() + " ya se encuentra asignado.");
        }

        Socio socio = socioMapper.toEntity(dto);
        Socio socioGuardado = socioRepository.save(socio);

        // Genera automáticamente el Grupo Familiar asociado encabezado por este socio
        String codigoGrupo = "GF-" + socioGuardado.getNumeroSocio() + "-" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        GrupoFamiliar grupo = new GrupoFamiliar(codigoGrupo, socioGuardado, "Grupo Familiar del Socio " + socioGuardado.obtenerNombreCompleto());
        grupoFamiliarRepository.save(grupo);
        socioGuardado.setGrupoFamiliar(grupo);

        return socioMapper.toResponseDTO(socioGuardado);
    }

    @Override
    public SocioResponseDTO actualizarSocio(Long id, SocioRequestDTO dto) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el socio con ID: " + id));

        // Validar unicidad de DNI si cambió
        if (!socio.getDni().equals(dto.getDni()) && socioRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("El DNI " + dto.getDni() + " ya está en uso.");
        }

        // Validar unicidad de Número de Socio si cambió
        if (!socio.getNumeroSocio().equals(dto.getNumeroSocio()) && socioRepository.existsByNumeroSocio(dto.getNumeroSocio())) {
            throw new IllegalArgumentException("El número de socio " + dto.getNumeroSocio() + " ya está en uso.");
        }

        socioMapper.updateEntityFromDTO(dto, socio);
        Socio actualizado = socioRepository.save(socio);
        return socioMapper.toResponseDTO(actualizado);
    }

    @Override
    @Transactional(readOnly = true)
    public SocioResponseDTO obtenerSocioPorId(Long id) {
        return socioRepository.findById(id)
                .map(socioMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el socio con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocioResponseDTO> obtenerTodosLosSocios() {
        return socioRepository.findAll().stream()
                .map(socioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SocioResponseDTO> buscarSocios(String query) {
        if (query == null || query.trim().isEmpty()) {
            return obtenerTodosLosSocios();
        }
        return socioRepository.buscarPorTermino(query.trim()).stream()
                .map(socioMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void darDeBajaSocio(Long id) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el socio con ID: " + id));
        socio.darDeBaja();
        socioRepository.save(socio);
    }

    @Override
    public void reactivarSocio(Long id) {
        Socio socio = socioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el socio con ID: " + id));
        socio.setActivo(true);
        socioRepository.save(socio);
    }
}

