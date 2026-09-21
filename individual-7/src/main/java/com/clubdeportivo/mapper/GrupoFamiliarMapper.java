package com.clubdeportivo.mapper;

import com.clubdeportivo.dto.response.GrupoFamiliarResponseDTO;
import com.clubdeportivo.entity.GrupoFamiliar;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * =========================================================================================
 * MAPPER: GrupoFamiliarMapper
 * =========================================================================================
 * Transforma agregaciones de GrupoFamiliar a su DTO proyectado con titular y adherentes.
 */
@Component
public class GrupoFamiliarMapper {

    private final FamiliarAdherenteMapper familiarMapper;

    public GrupoFamiliarMapper(FamiliarAdherenteMapper familiarMapper) {
        this.familiarMapper = familiarMapper;
    }

    public GrupoFamiliarResponseDTO toResponseDTO(GrupoFamiliar g) {
        if (g == null) {
            return null;
        }
        GrupoFamiliarResponseDTO dto = new GrupoFamiliarResponseDTO();
        dto.setId(g.getId());
        dto.setCodigo(g.getCodigo());
        dto.setFechaCreacion(g.getFechaConstitucion());
        dto.setObservaciones(g.getObservaciones());

        if (g.getTitular() != null) {
            dto.setTitularId(g.getTitular().getId());
            dto.setTitularNombre(g.getTitular().obtenerNombreCompleto());
            dto.setTitularNumeroSocio(g.getTitular().getNumeroSocio());
            dto.setTitularDni(g.getTitular().getDni());
            dto.setTitularAlDia(g.getTitular().estaAlDia());
        }

        dto.setCantidadTotalIntegrantes(g.cantidadIntegrantes());

        if (g.getIntegrantes() != null) {
            dto.setAdherentes(
                g.getIntegrantes().stream()
                    .map(familiarMapper::toResponseDTO)
                    .collect(Collectors.toList())
            );
        }
        return dto;
    }
}
