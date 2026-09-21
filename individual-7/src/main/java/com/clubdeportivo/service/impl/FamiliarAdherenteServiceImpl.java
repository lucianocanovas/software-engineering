package com.clubdeportivo.service.impl;

import com.clubdeportivo.dto.request.FamiliarAdherenteRequestDTO;
import com.clubdeportivo.dto.response.FamiliarAdherenteResponseDTO;
import com.clubdeportivo.entity.FamiliarAdherente;
import com.clubdeportivo.entity.GrupoFamiliar;
import com.clubdeportivo.entity.Socio;
import com.clubdeportivo.mapper.FamiliarAdherenteMapper;
import com.clubdeportivo.repository.FamiliarAdherenteRepository;
import com.clubdeportivo.repository.GrupoFamiliarRepository;
import com.clubdeportivo.repository.SocioRepository;
import com.clubdeportivo.service.FamiliarAdherenteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * =========================================================================================
 * SERVICIO IMPL: FamiliarAdherenteServiceImpl
 * =========================================================================================
 * Lógica de incorporación y control de familiares adherentes vinculados a un socio titular.
 */
@Service
@Transactional
public class FamiliarAdherenteServiceImpl implements FamiliarAdherenteService {

    private final FamiliarAdherenteRepository familiarRepository;
    private final SocioRepository socioRepository;
    private final GrupoFamiliarRepository grupoFamiliarRepository;
    private final FamiliarAdherenteMapper familiarMapper;

    public FamiliarAdherenteServiceImpl(FamiliarAdherenteRepository familiarRepository,
                                       SocioRepository socioRepository,
                                       GrupoFamiliarRepository grupoFamiliarRepository,
                                       FamiliarAdherenteMapper familiarMapper) {
        this.familiarRepository = familiarRepository;
        this.socioRepository = socioRepository;
        this.grupoFamiliarRepository = grupoFamiliarRepository;
        this.familiarMapper = familiarMapper;
    }

    @Override
    public FamiliarAdherenteResponseDTO agregarAdherente(FamiliarAdherenteRequestDTO dto) {
        if (familiarRepository.existsByDni(dto.getDni())) {
            throw new IllegalArgumentException("Ya existe una persona registrada con el DNI: " + dto.getDni());
        }

        Socio socioTitular = socioRepository.findById(dto.getSocioTitularId())
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el socio titular con ID: " + dto.getSocioTitularId()));

        GrupoFamiliar grupo = socioTitular.getGrupoFamiliar();
        if (grupo == null) {
            grupo = grupoFamiliarRepository.findByTitularId(socioTitular.getId())
                    .orElseGet(() -> {
                        GrupoFamiliar nuevo = new GrupoFamiliar("GF-" + socioTitular.getNumeroSocio(), socioTitular, "Grupo Familiar");
                        return grupoFamiliarRepository.save(nuevo);
                    });
        }

        FamiliarAdherente adherente = familiarMapper.toEntity(dto);
        adherente.setGrupoFamiliar(grupo);

        FamiliarAdherente guardado = familiarRepository.save(adherente);
        grupo.agregarIntegrante(guardado);

        return familiarMapper.toResponseDTO(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public FamiliarAdherenteResponseDTO obtenerAdherentePorId(Long id) {
        return familiarRepository.findById(id)
                .map(familiarMapper::toResponseDTO)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el adherente con ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FamiliarAdherenteResponseDTO> listarPorGrupoFamiliar(Long grupoFamiliarId) {
        return familiarRepository.findByGrupoFamiliarId(grupoFamiliarId).stream()
                .map(familiarMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarAdherente(Long id) {
        FamiliarAdherente adherente = familiarRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el adherente con ID: " + id));
        if (adherente.getGrupoFamiliar() != null) {
            adherente.getGrupoFamiliar().removerIntegrante(adherente);
        }
        familiarRepository.delete(adherente);
    }
}

